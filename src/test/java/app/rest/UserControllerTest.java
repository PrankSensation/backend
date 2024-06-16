package app.rest;

import app.exeptions.AlreadyExistsException;
import app.models.Roles;
import app.models.Sector;
import app.models.User;
import app.repositories.SectorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private UserController userController = new UserController();

    @Autowired
    SectorRepository sectorRepository;

    private User user1;
    private User user2;
    private User responseUser1;
    private User responseUser2;

    /*@BeforeEach
    public void setUp() throws AlreadyExistsException, ChangeSetPersister.NotFoundException {

        user1 = new User("firstName", "lastName", "email@maildawss.com1", "password", Roles.USER,"test", sectorRepository.create(new Sector("test")),"test","test","test","test");
        user2 = new User("firstName2", "lastName2", "email@mail.com2", "password2", Roles.USER,"test",sectorRepository.create(new Sector("test")),"test","test","test","test");


        if(userController.findByUuid(user1.getUuid())!=null){
            userController.deleteByUuid(userController.getUserByEmail(user1.getEmail()).getUuid());

        }
        if(userController.findByUuid(user2.getUuid())!=null){
            userController.deleteByUuid(userController.getUserByEmail(user2.getEmail()).getUuid());

        }

        responseUser1 = userController.save(user1).getBody();
        responseUser2 = userController.save(user2).getBody();
    }

    @AfterEach
    public void tearDown() throws ChangeSetPersister.NotFoundException {
        userController.deleteByUuid(userController.getUserByEmail(user1.getEmail()).getUuid());
        userController.deleteByUuid(userController.getUserByEmail(user2.getEmail()).getUuid());

    }

    @Test
    public void can_add_user() throws AlreadyExistsException {
        User newUser = new User("firstName", "lastName", "email2@mail.com", "password", Roles.USER,"test",sectorRepository.create(new Sector("test")),"test","test","test","test");
        ResponseEntity<User> responseEntity = userController.save(newUser);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        userController.deleteByUuid(Objects.requireNonNull(responseEntity.getBody()).getUuid());
    }

    @Test
    public void can_add_user_admin() throws AlreadyExistsException {
        User newUser = new User("firstName", "lastName", "email3@mail.com", "password", Roles.ADMIN,"test",sectorRepository.create(new Sector("test")),"test","test","test","test");
        ResponseEntity<User> responseEntity = userController.saveAdmin(newUser);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        userController.deleteByUuid(Objects.requireNonNull(responseEntity.getBody()).getUuid());
    }
    @Test()
    public void can_not_add_user_when_email_already_in_use() {
        try {
            ResponseEntity<User> responseEntity2 = userController.save(user2);
        } catch (Exception e) {
            assertSame("User with this email already exists", e.getMessage());

        }

    }

    @Test()
    public void can_remove_user() throws AlreadyExistsException {
//        Add new user to database
        User user3 = new User("firstName3", "lastName3", "email@mail.com3", "password3", Roles.USER,"test",sectorRepository.create(new Sector("test")),"test","test","test","test");
        User createdUser = userController.save(user3).getBody();

//       delete the user
        assert createdUser != null;
        ResponseEntity<User> deletedUser = userController.deleteByUuid(createdUser.getUuid());
        assertTrue(deletedUser.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void can_find_user_by_uuid() throws ChangeSetPersister.NotFoundException {
        User response = userController.findByUuid(responseUser1.getUuid());

        assertNotNull(response);
        assertEquals(responseUser1.getUuid(), response.getUuid());
        assertEquals(responseUser1.getFirstName(), response.getFirstName());
    }
    @Test
    public void can_change_user_role() throws ChangeSetPersister.NotFoundException {
        // Change user role
        ResponseEntity<User> updatedUserResponse = userController.changeUserRole(responseUser1.getUuid(), "ADMIN");
        User updatedUser = updatedUserResponse.getBody();

        // Verify the change
        assertNotNull(updatedUser);
        assertEquals(Roles.ADMIN, updatedUser.getRole());
        assertEquals(responseUser1.getUuid(), updatedUser.getUuid());
    }
*/


}
