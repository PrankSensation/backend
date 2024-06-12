package app.security;


import app.config.APIConfig;
import app.models.JWToken;
import app.models.Roles;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    @Autowired
    APIConfig apiConfig;

    private static final Pattern UUID_PATTERN = Pattern.compile("/([0-9a-fA-F-]{36})(/|$)");

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException, java.io.IOException {
        String servletPath = request.getServletPath();

        if ((HttpMethod.OPTIONS.matches(request.getMethod()) ||(
                this.apiConfig.SECURED_PATHS.stream().noneMatch(path -> servletPath.startsWith((String) path))) &&
                this.apiConfig.ADMIN_SECURED_PATHS.stream().noneMatch(path -> servletPath.startsWith((String) path)) &&
                this.apiConfig.PERSONAL_PATHS.stream().noneMatch(path -> servletPath.startsWith((String) path))) ) {
            chain.doFilter(request, response);
            return;
        }

        String encryptedToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (encryptedToken == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No token provided, you need to logon first");
            return;
        }

        JWToken token = null;
        try {
            token = JWToken.decode(encryptedToken.replace("Bearer ", ""),this.apiConfig.getIssuer(), this.apiConfig.getPassphrase());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage() + " You need to login first.");
            return;
        }

        if(this.apiConfig.ADMIN_SECURED_PATHS.stream().anyMatch(path -> servletPath.startsWith((String) path))){
            if(token.role != Roles.ADMIN){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Dont try to reach an admin page as a non admin.");
                return;
            }
        }

        if (this.apiConfig.PERSONAL_PATHS.stream().anyMatch(path -> servletPath.startsWith((String) path))) {
            Matcher matcher = UUID_PATTERN.matcher(servletPath);
            if (matcher.find()) {
                String uuidFromPath = matcher.group(1);
                if (!uuidFromPath.equals(token.accountId)) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UUID in the request path does not match the token's UUID.");
                    return;
                }
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No UUID found in the request path.");
                return;
            }
        }
        request.setAttribute("token", token);

        chain.doFilter(request, response);
    }

}
