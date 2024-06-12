
package app.rest;

import app.services.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class EmailControllerTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailController emailController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSendMail() {
        String recipient = "daancompier@gmail.com";
        String subject = "test een email";
        String body = "Kijk eens wat een mail";

        String result = emailController.sendMail();

        assertEquals("Email sent", result);
        verify(emailService, times(1)).sendEmail(eq(recipient), eq(subject), eq(body));
    }
}
