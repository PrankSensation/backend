package app.rest;

import app.models.view;
import app.security.PasswordEncoder;
import app.config.APIConfig;
import app.models.JWToken;
import app.models.User;
import app.repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private APIConfig apiConfig;

    @PostMapping("/login")
    @JsonView(view.User.class)
    public ResponseEntity<User> login(@RequestBody ObjectNode body) throws Exception {
        String email = body.get("email").asText();
        String password = body.get("password").asText();



        User user = userRepository.findByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Argon2PasswordEncoder argon2PasswordEncoder = new PasswordEncoder();

        if (argon2PasswordEncoder.matches(password, user.getPassword())) {
            JWToken jwToken = new JWToken(user.getFirstName(), user.getUuid(), user.getRole());
            String tokenString = jwToken.encode(
                    apiConfig.getIssuer(),
                    apiConfig.getPassphrase(),
                    apiConfig.getTokenDurationOfValidity()
            );
            return ResponseEntity.accepted()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                    .body(user);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }
}
