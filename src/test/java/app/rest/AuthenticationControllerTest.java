package app.rest;

import app.config.APIConfig;
import app.models.JWToken;
import app.models.Roles;
import app.models.Sector;
import app.models.User;
import app.repositories.SectorRepository;
import app.repositories.UserRepository;
import app.rest.AuthenticationController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.Transactional;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class AuthenticationControllerTest {

    @Autowired
    SectorRepository sectorRepository;

    private static final Logger log = LoggerFactory.getLogger(AuthenticationControllerTest.class);

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private APIConfig apiConfig;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public AuthenticationControllerTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLogin_ValidCredentials() throws Exception {
        // Prepare test data
        String email = "test@example.com";
        String password = "password";
        Sector sector = sectorRepository.create(new Sector("test"));
        User user = new User("name", "name", email, password, Roles.USER,"test",sector,"test","test");

        // Mock userRepository behavior
        when(userRepository.findByEmail(email)).thenReturn(user);

        // Mock APIConfig
        when(apiConfig.getIssuer()).thenReturn("me, the test engine.");
        when(apiConfig.getPassphrase()).thenReturn("This is a temporary fake password key for testing, please dont look so much into it.");
        when(apiConfig.getTokenDurationOfValidity()).thenReturn(28800); // Example token duration

        // Prepare request body
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("email", email);
        requestBody.put("password", password);

        // Call the controller method
        ResponseEntity<User> response = authenticationController.login(requestBody);

        // Verify response
        assertEquals("", HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("", user, response.getBody());

        // Verify JWT token in header
        String tokenString = response.getHeaders().getFirst("Authorization");
        assertNotNull("JWT token is not null", tokenString);

        // Verify JWT token content
        Jws<Claims> jwtClaims = Jwts.parserBuilder()
                .setSigningKey(apiConfig.getPassphrase().getBytes())
                .build()
                .parseClaimsJws(tokenString.replace("Bearer ", ""));

        assertNotNull("JWT token content is not null", jwtClaims);
        assertEquals("JWT issuer is correct", apiConfig.getIssuer(), jwtClaims.getBody().getIssuer());
        // Add more assertions for JWT token claims as needed
    }

    @Test
    public void testLogin_InvalidCredentials() throws Exception {
        // Prepare test data
        String email = "test@example.com";
        String password = "invalid_password";
        User user = new User("name", "name", email, "password", Roles.USER,"test","test","test");

        // Mock userRepository behavior
        when(userRepository.findByEmail(email)).thenReturn(user);

        // Prepare request body
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("email", email);
        requestBody.put("password", password);

        // Call the controller method
        ResponseEntity<User> response = authenticationController.login(requestBody);

        // Verify response
        assertEquals("", HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testLogin_UserNotFound() throws Exception {
        // Prepare test data
        String email = "non_existing@example.com";
        String password = "password";

        // Mock userRepository behavior
        when(userRepository.findByEmail(email)).thenReturn(null);

        // Prepare request body
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("email", email);
        requestBody.put("password", password);

        // Call the controller method
        ResponseEntity<User> response = authenticationController.login(requestBody);

        // Verify response
        assertEquals("", HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    public void testLogin_ReturnsValidToken() throws Exception {
        // Prepare test data
        String email = "test@example.com";
        String password = "password";
        Sector sector = sectorRepository.create(new Sector("test"));
        User user = new User("name", "name", email, password, Roles.USER,"test",sector,"test","test");

        // Mock userRepository behavior
        when(userRepository.findByEmail(email)).thenReturn(user);

        // Mock APIConfig
        when(apiConfig.getIssuer()).thenReturn("me, the test engine.");
        when(apiConfig.getPassphrase()).thenReturn("This is a temporary fake password key for testing, please dont look so much into it.");
        when(apiConfig.getTokenDurationOfValidity()).thenReturn(28800); // Example token duration

        // Prepare request body
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("email", email);
        requestBody.put("password", password);

        // Call the controller method
        ResponseEntity<User> response = authenticationController.login(requestBody);

        // Verify response
        assertEquals("",HttpStatus.ACCEPTED, response.getStatusCode());

        // Verify JWT token in header
        String tokenString = response.getHeaders().getFirst("Authorization");
        assertNotNull("Token returned null",tokenString);
        assertTrue(tokenString.startsWith("Bearer "));

        // Decode the token and verify
        JWToken decodedToken = JWToken.decode(tokenString.replace("Bearer ", ""), apiConfig.getIssuer(), apiConfig.getPassphrase());
        assertNotNull("email mustnt be null",decodedToken.callName);
        assertEquals("id changed? ",user.getUuid(), decodedToken.accountId);
        assertEquals("Wrong role",Roles.USER.toString(), decodedToken.role.toString());
    }
}
