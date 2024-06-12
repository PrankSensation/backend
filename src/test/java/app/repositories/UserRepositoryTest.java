package app.repositories;

import app.models.Roles;
import app.models.Sector;
import app.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class UserRepositoryTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    SectorRepository sectorRepository;

    private User user1;
    private User user2;
    private User responseUser1;
    private User responseUser2;

    @BeforeEach
    public void setUp() {
        user1 = new User("firstName", "lastName", "email1@mail.com", "password", Roles.USER,"test","test","test");
        user1.setRandomUuid();
        user1.setPassword(user1.hashPassword("password"));

        user2 = new User("firstName2", "lastName2", "email2@mail.com", "password2", Roles.USER,"test","test","test");
        user2.setRandomUuid();
        user2.setPassword(user2.hashPassword("password2"));

        responseUser1 = userRepository.create(user1);
        responseUser2 = userRepository.create(user2);
    }

    @AfterEach
    public void tearDown() {
        userRepository.delete(responseUser1);
        userRepository.delete(responseUser2);
    }

    @Test
    public void can_add_user() {
        Sector sector = sectorRepository.create(new Sector("test"));
        User newUser = new User("firstName", "lastName", "email@mail.com", "password", Roles.USER,"test",sector,"test","test");
        User response = userRepository.create(newUser);

        assertNotNull(response);
        em.remove(response);
    }

    @Test
    public void can_find_user_by_email() {
        // search user
        User response = userRepository.findByEmail("email1@mail.com");

        assertNotNull(response);
        assertSame(response, user1);

    }

    @Test
    public void can_find_all_users() {

        List<User> users = userRepository.findAll();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));

    }

}
