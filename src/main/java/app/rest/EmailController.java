package app.rest;

import app.services.EmailService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    private EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping("/sendMail")
    public String sendMail() {
        emailService.sendEmail("daancompier@gmail.com", "test een email", "Kijk eens wat een mail");
        return "Email sent";
    }
}
