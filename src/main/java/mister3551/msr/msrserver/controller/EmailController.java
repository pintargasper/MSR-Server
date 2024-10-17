package mister3551.msr.msrserver.controller;

import mister3551.msr.msrserver.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    /*@PostMapping("/user/confirm")
    public String confirmEmail(Authentication authentication, @Valid @RequestBody EmailToken emailToken) {
        return emailService.confirmEmail(authentication, emailToken);
    }

    @PostMapping("/user/password/forgot")
    public String resetEmail(@Valid @RequestBody EmailAddress emailAddress) throws IOException, MessagingException {
        return emailService.resetEmail(emailAddress);
    }*/
}