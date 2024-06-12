package app.models;

import app.security.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserTest {

    public User user1;
    public PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        user1 = new User("firstName", "lastName", "email@mail.com", "password", Roles.USER,"test","test","test");
        passwordEncoder = new PasswordEncoder();
    }


    @Test
    void new_user_get_random_uuid() {
        // the uuid was not given in the constructor

        assertNotNull(user1.getUuid());
    }

    @Test
    void password_gets_hashed() {
        //check if the password isn't the same
        assertNotSame("password", user1.getPassword());

        // check if the password is indeed hashed propperly
        assertTrue(passwordEncoder.matches("password", user1.getPassword()));

    }
}
