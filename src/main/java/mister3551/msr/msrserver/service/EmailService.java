package mister3551.msr.msrserver.service;

import mister3551.msr.msrserver.record.EmailAddress;
import mister3551.msr.msrserver.record.EmailToken;
import mister3551.msr.msrserver.repository.UserRepository;
import mister3551.msr.msrserver.security.entity.User;
import mister3551.msr.msrserver.security.record.SignUpRequest;
import mister3551.msr.msrserver.security.repository.UsersRepository;
import mister3551.msr.msrserver.security.security.CustomUser;
import mister3551.msr.msrserver.security.service.TokenService;
import mister3551.msr.msrserver.service.email.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class EmailService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final UsersRepository usersRepository;

    @Autowired
    public EmailService(UserRepository userRepository, TokenService tokenService, UsersRepository usersRepository) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.usersRepository = usersRepository;
    }

    public String confirmEmail(Authentication authentication, EmailToken emailToken) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        return userRepository.confirmEmailAddress(customUser.getUsername(), customUser.getEmailAddress(), emailToken.token()) == 1 ? "SUCCESS" : "Something went wrong";
    }

    public void sendConfirmEmail(SignUpRequest signUpRequest, String generatedToken) throws IOException, javax.mail.MessagingException {
        final String sender = "email";
        final String password = "password";

        String subject = "Confirm Email address";

        String fullName = replaceSpecialCharacters(signUpRequest.fullName());
        String token = replaceSpecialCharacters(generatedToken);

        String body = new String(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("templates/confirm.html")).readAllBytes(), StandardCharsets.UTF_8)
                .replace("{{name}}", fullName)
                .replace("{{token}}", token);

        EmailSender.send(sender, password, signUpRequest.emailAddress(), subject, body);
    }

    public String resetEmail(EmailAddress emailAddress) throws IOException, MessagingException {
        final String sender = "email";
        final String password = "password";

        String subject = "Reset password";

        User user = usersRepository.findByEmailAddress(emailAddress.emailAddress());

        SignUpRequest signUpRequest = new SignUpRequest(
                user.getFullName(),
                user.getUsername(),
                user.getEmailAddress(),
                null,
                null,
                null,
                user.getCountry()
        );

        String token = tokenService.generateEmailToken(signUpRequest, "CHANGE_EMAIL");

        if (userRepository.setPasswordResetToken(user.getUsername(), user.getEmailAddress(), token) == 1) {

            String body = new String(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("templates/resetPassword.html")).readAllBytes(), StandardCharsets.UTF_8)
                    .replace("{{name}}", user.getFullName())
                    .replace("{{token}}", token);
            return EmailSender.send(sender, password, emailAddress.emailAddress(), subject, body) ? "SUCCESS" : "Something went wrong";
        }
        return "Something went wrong";
    }

    private String replaceSpecialCharacters(String string) {
        StringBuilder stringBuilder = new StringBuilder();

        for (char chr : string.toCharArray()) {
            stringBuilder.append("&#");
            stringBuilder.append((int) chr);
            stringBuilder.append(";");
        }
        return stringBuilder.toString();
    }
}