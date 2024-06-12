package app.config;

  import org.springframework.beans.factory.annotation.Value;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.http.HttpHeaders;
  import org.springframework.web.servlet.config.annotation.CorsRegistry;
  import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

  import java.util.Arrays;
  import java.util.List;

@Configuration
public class APIConfig implements WebMvcConfigurer {
  public static final List SECURED_PATHS =  Arrays.asList(new String[]{"/question","/answer","/result"});
  public static final List ADMIN_SECURED_PATHS = Arrays.asList(new String[]{"/admin"});

  public static final List PERSONAL_PATHS = Arrays.asList(new String[]{"/personal"});
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOriginPatterns("http://localhost:*")
      .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")
      .allowedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE)
      .exposedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE)
      .allowCredentials(true);
  }
  @Value("${jwt.issuer}")
  private String issuer;

  @Value("${jwt.pass-phrase}")
  private String passphrase;

  @Value("${jwt.duration-of-validity}")
  private int expiration;

  public String getIssuer() {
    return issuer;
  }

  public int getTokenDurationOfValidity() {
    return expiration;
  }

  public String getPassphrase() {
    return passphrase;
  }
}
