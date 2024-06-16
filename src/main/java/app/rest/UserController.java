package app.rest;

import app.exeptions.AlreadyExistsException;
import app.models.Roles;
import app.models.Sector;
import app.models.User;
import app.models.view;
import app.repositories.EntityRepository;
import app.repositories.SectorRepository;
import app.repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
public class UserController implements EntityRepository<User> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SectorRepository sectorRepository;

    @GetMapping("/admin/user/all")
    @JsonView(view.User.class)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @PostMapping("user")
    @JsonView(view.User.class)
    public ResponseEntity<User> save(@RequestBody User user) throws AlreadyExistsException {
        // Check if user with this email already exists
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new AlreadyExistsException("User with this email already exists");
        }
        // Create a user id and hash the password
        user.setRandomUuid();
        user.setPassword(user.hashPassword(user.getPassword()));
        user.setRole(Roles.USER);
        User response = userRepository.create(user);

        // Manually construct the URI
        URI location = URI.create("/uuid/" + response.getUuid());

        return ResponseEntity.created(location).body(response);
    }
    @PostMapping("/admin/user")
    @JsonView(view.User.class)
    public ResponseEntity<User> saveAdmin(@RequestBody User user) throws AlreadyExistsException {
        // Check if user with this email already exists
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new AlreadyExistsException("User with this email already exists");
        }

        // Create a user id and hash the password
        user.setRandomUuid();
        user.setPassword(user.hashPassword(user.getPassword()));

        User response = userRepository.create(user);

        // Manually construct the URI
        URI location = URI.create("/uuid/" + response.getUuid());

        return ResponseEntity.created(location).body(response);
    }


    @GetMapping({"/user/{uuid}", "/personal/user/{uuid}"})
    @JsonView(view.User.class)
    public User findByUuid(@PathVariable String uuid) throws ChangeSetPersister.NotFoundException {
        User response = userRepository.findByUuidWithSector(uuid);
        if (response == null) throw new ChangeSetPersister.NotFoundException();
        return response;
    }

    @GetMapping({"/user/{uuid}", "/sector/personal/user/{uuid}"})
    @JsonView(view.User.class)
    public Sector findSectorByUuid(@PathVariable String uuid) throws ChangeSetPersister.NotFoundException {
        Sector response = userRepository.findSectorByUserUuid(uuid);
        if (response == null) throw new ChangeSetPersister.NotFoundException();
        return response;
    }


    @GetMapping("/user/email/{email}")
    @JsonView(view.User.class)
    public User getUserByEmail(@PathVariable String email) throws ChangeSetPersister.NotFoundException {
        User response = userRepository.findByEmail(email);
        if (response == null) throw new ChangeSetPersister.NotFoundException();
        return response;
    }

    @DeleteMapping({"/admin/user/{uuid}", "/personal/user/{uuid}"})
    @JsonView(view.User.class)
    public ResponseEntity<User> deleteByUuid(@PathVariable String uuid) {
        User user = userRepository.findByUuid(uuid);

        User response;
        response = userRepository.delete(user);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @JsonView(view.User.class)
    @PutMapping("/personal/user/{uuid}")
    public ResponseEntity<User> updateUser(@RequestBody User user) throws ChangeSetPersister.NotFoundException {
       User existingUser = userRepository.findByUuid(user.getUuid());
       existingUser.setSector(user.getSector());
       existingUser.setCompanyName(user.getCompanyName());
       existingUser.setCompanySize(user.getCompanySize());
       existingUser.setCompanySizeMarketing(user.getCompanySizeMarketing());
       existingUser.setIncome(user.getIncome());
       existingUser.setPurchaseQuotient(user.getPurchaseQuotient());
       user.setPurchaseQuotient(user.getPurchaseQuotient());
       User response = userRepository.update(existingUser);
       return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/admin/user/{uuid}/role/{newRole}")
    @JsonView(view.User.class)
    public ResponseEntity<User> changeUserRole(@PathVariable String uuid, @PathVariable String newRole) throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findByUuid(uuid);

        if (user == null) {
            throw new ChangeSetPersister.NotFoundException();
        }
        user.setRole(Roles.valueOf((String) newRole)); // Assuming User class has a setRole method

        User updatedUser = userRepository.update(user);

        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }
    @PutMapping({"/admin/user/{uuid}/useData/{useData}","/personal/user/{uuid}/useData/{useData}"})
    @JsonView(view.User.class)
    public ResponseEntity<User> changeUseData(@PathVariable String uuid, @PathVariable boolean useData) throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findByUuid(uuid);

        if (user == null) {
            throw new ChangeSetPersister.NotFoundException();
        }
        User updatedUser = userRepository.update(user);

        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }
}
