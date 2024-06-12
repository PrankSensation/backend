package app.rest;

import app.services.EmailService;
import app.models.PasswordResetToken;
import app.models.User;
import app.repositories.PasswordTokenRepository;
import app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@RestController
public class PasswordResetTokenController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserController userController;

    public PasswordResetTokenController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("user/resetPassword")
    public ResponseEntity<PasswordResetToken> save(@RequestParam String email) throws Exception {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }
        String token = UUID.randomUUID().toString();

        PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);
        PasswordResetToken response = passwordTokenRepository.save(passwordResetToken);

        if (response == null) {
            throw new Exception("Failed to save password reset token");
        }

        emailService.sendHtmlEmail(user.getEmail(), "Wachtwoord veranderen", emailService.resetPasswordText(response.getUuid()));


        return ResponseEntity.status(HttpStatus.CREATED).body(passwordResetToken);
    }


    @GetMapping("user/resetPassword/{uuid}")
    public PasswordResetToken findByUuid(@PathVariable String uuid) throws Exception {
        PasswordResetToken passwordResetToken = passwordTokenRepository.findByUuid(uuid);
        if (passwordResetToken == null) {
            throw new Exception("Token not found");
        }

        if (passwordResetToken.getExpirationDate().before(new Date())) {
            // if expired delete the token
            passwordTokenRepository.deleteByUuid(uuid);
            throw new Exception("Token is expired");
        }

        return passwordResetToken;
    }

    @PostMapping("user/resetPassword/{uuid}")
    public ResponseEntity<User> update(@RequestBody User user, @PathVariable String uuid) throws Exception {

        User response = userRepository.findByUuid(user.getUuid());

        PasswordResetToken passwordResetToken = findByUuid(uuid);
        if (passwordResetToken == null) {
            throw new Exception("Token not found");
        }

        if (response == null) {
            throw new Exception("User not found");
        }

        if (!Objects.equals(passwordResetToken.getUserUuid(), response.getUuid())) {
            throw new Exception("Invalid Token");
        }

        response.setPassword(response.hashPassword(user.getPassword()));

        return userController.updateUser(response);


    }

    @DeleteMapping("user/resetPassword/{uuid}")
    public ResponseEntity<PasswordResetToken> delete(@PathVariable String uuid) throws Exception {
        PasswordResetToken passwordResetToken = findByUuid(uuid);
        if (passwordResetToken == null) {
            throw new Exception("Token not found");
        }
        passwordTokenRepository.deleteByUuid(uuid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}


