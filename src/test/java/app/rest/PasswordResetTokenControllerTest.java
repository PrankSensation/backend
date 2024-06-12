package app.rest;

import app.services.EmailService;
import app.models.PasswordResetToken;

import app.models.User;
import app.repositories.PasswordTokenRepository;
import app.repositories.UserRepository;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Field;


import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class PasswordResetTokenControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordTokenRepository passwordTokenRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private PasswordResetTokenController passwordResetTokenController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        passwordResetTokenController = new PasswordResetTokenController(emailService);
        injectMockRepository(passwordResetTokenController, passwordTokenRepository);
        injectMockRepository(passwordResetTokenController, userRepository);
    }

    private void injectMockRepository(PasswordResetTokenController controller, Object mockRepository) {
        try {
            Field[] fields = PasswordResetTokenController.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getType().isAssignableFrom(mockRepository.getClass())) {
                    field.set(controller, mockRepository);
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSave_Success() throws Exception {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(user);

        PasswordResetToken token = new PasswordResetToken(user, UUID.randomUUID().toString());
        when(passwordTokenRepository.save(any(PasswordResetToken.class))).thenReturn(token);

        ResponseEntity<PasswordResetToken> responseEntity = passwordResetTokenController.save(email);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        PasswordResetToken savedToken = responseEntity.getBody();
        assertEquals(user.getUuid(), savedToken.getUserUuid());
        assertNotNull(savedToken.getToken());
        assertNotNull(savedToken.getExpirationDate());

        verify(passwordTokenRepository, times(1)).save(any(PasswordResetToken.class));

    }


    @Test
    void testSave_UserNotFound() throws MessagingException {
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(null);

        assertThrows(Exception.class, () -> passwordResetTokenController.save(email));
        verify(passwordTokenRepository, never()).save(any());
        verify(emailService, never()).sendHtmlEmail(any(), any(), any());
    }

    @Test
    void testFindByUuid_Success() throws Exception {
        String uuid = UUID.randomUUID().toString();
        PasswordResetToken token = new PasswordResetToken();

        //        Set time an hour later
        Calendar calender = Calendar.getInstance();
        calender.add(Calendar.HOUR_OF_DAY, 1);

        token.setExpirationDate(calender.getTime());
        when(passwordTokenRepository.findByUuid(uuid)).thenReturn(token);

        PasswordResetToken result = passwordResetTokenController.findByUuid(uuid);

        assertNotNull(result);
        assertEquals(token, result);
    }

    @Test
    void testFindByUuid_TokenNotFound() {
        String uuid = "nonexistent_uuid";
        when(passwordTokenRepository.findByUuid(uuid)).thenReturn(null);

        assertThrows(Exception.class, () -> passwordResetTokenController.findByUuid(uuid));
    }

    @Test
    void testFindByUuid_TokenExpired() {
        String uuid = UUID.randomUUID().toString();
        PasswordResetToken expiredToken = new PasswordResetToken();
        expiredToken.setExpirationDate(new Date(System.currentTimeMillis() - 3600 * 1000)); // One hour ago
        when(passwordTokenRepository.findByUuid(uuid)).thenReturn(expiredToken);

        assertThrows(Exception.class, () -> passwordResetTokenController.findByUuid(uuid));
        verify(passwordTokenRepository, times(1)).deleteByUuid(uuid);
    }

    @Test
    void testUpdate_TokenNotFound() {
        String uuid = "nonexistent_uuid";
        User user = new User();

        assertThrows(Exception.class, () -> passwordResetTokenController.update(user, uuid));
    }

    @Test
    void testUpdate_UserNotFound() {
        String uuid = UUID.randomUUID().toString();
        User user = new User();
        PasswordResetToken token = new PasswordResetToken(new User(), uuid);
        when(passwordTokenRepository.findByUuid(uuid)).thenReturn(token);
        when(userRepository.findByUuid(uuid)).thenReturn(null);

        assertThrows(Exception.class, () -> passwordResetTokenController.update(user, uuid));
    }

    @Test
    void testUpdate_InvalidToken() {
        String uuid = UUID.randomUUID().toString();
        User user = new User();
        PasswordResetToken token = new PasswordResetToken(new User(), UUID.randomUUID().toString()); // Different UUID
        when(passwordTokenRepository.findByUuid(uuid)).thenReturn(token);
        when(userRepository.findByUuid(uuid)).thenReturn(user);

        assertThrows(Exception.class, () -> passwordResetTokenController.update(user, uuid));
    }

    @Test
    void testDelete_Success() throws Exception {
        String uuid = UUID.randomUUID().toString();
        PasswordResetToken token = new PasswordResetToken();

//        Set time an hour later
        Calendar calender = Calendar.getInstance();
        calender.add(Calendar.HOUR_OF_DAY, 1);

        token.setExpirationDate(calender.getTime());
        when(passwordTokenRepository.findByUuid(uuid)).thenReturn(token);

        ResponseEntity<PasswordResetToken> responseEntity = passwordResetTokenController.delete(uuid);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(passwordTokenRepository, times(1)).deleteByUuid(uuid);
    }

    @Test
    void testDelete_TokenNotFound() {
        String uuid = "nonexistent_uuid";
        when(passwordTokenRepository.findByUuid(uuid)).thenReturn(null);

        assertThrows(Exception.class, () -> passwordResetTokenController.delete(uuid));
        verify(passwordTokenRepository, never()).deleteByUuid(uuid);
    }
}
