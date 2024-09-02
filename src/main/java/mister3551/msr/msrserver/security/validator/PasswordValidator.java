package mister3551.msr.msrserver.security.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mister3551.msr.msrserver.record.ResetPassword;
import mister3551.msr.msrserver.record.ResetPasswordWithToken;
import mister3551.msr.msrserver.security.entity.User;
import mister3551.msr.msrserver.security.record.SignUpRequest;
import mister3551.msr.msrserver.security.repository.UsersRepository;
import mister3551.msr.msrserver.security.security.CustomUser;
import mister3551.msr.msrserver.security.service.AuthService;
import mister3551.msr.msrserver.security.validator.anno.ValidPassword;
import mister3551.msr.msrserver.security.validator.impl.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
public class PasswordValidator implements ConstraintValidator<ValidPassword, Object>, ConstraintViolation {

    private final AuthService authService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UsersRepository usersRepository;
    private String lengthMessage;
    private String uppercaseMessage;
    private String lowercaseMessage;
    private String numbersMessage;
    private String specialCharacterMessage;
    private String currentPassword;
    private String passwordChangeTimer;

    @Autowired
    public PasswordValidator(AuthService authService, BCryptPasswordEncoder bCryptPasswordEncoder, UsersRepository usersRepository) {
        this.authService = authService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.usersRepository = usersRepository;
    }

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        this.lengthMessage = constraintAnnotation.lengthMessage();
        this.uppercaseMessage = constraintAnnotation.uppercaseMessage();
        this.lowercaseMessage = constraintAnnotation.lowercaseMessage();
        this.numbersMessage = constraintAnnotation.numbersMessage();
        this.specialCharacterMessage = constraintAnnotation.specialCharacterMessage();
        this.currentPassword = constraintAnnotation.currentMessage();
        this.passwordChangeTimer = constraintAnnotation.passwordChangeTimer();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext){

        String password = "";
        String confirmPassword = "";

        if (object instanceof SignUpRequest signUpRequest) {
            password = signUpRequest.password();
            confirmPassword = signUpRequest.confirmPassword();
        } else if (object instanceof ResetPassword resetPassword) {

            Authentication authentication = authService.getAuthentication();
            CustomUser customUser = (CustomUser) authentication.getPrincipal();
            User user = usersRepository.findByUsernameOrEmailAddress(customUser.getUsername(), customUser.getEmailAddress());

            if (user != null && user.getPasswordChangeTimer() != null && user.getPasswordChangeTimer().isAfter(LocalDateTime.now())) {

                long remainingMinutes  = Duration.between(LocalDateTime.now(), user.getPasswordChangeTimer()).toMinutes();
                long hours = TimeUnit.MINUTES.toHours(remainingMinutes);
                long minutes = remainingMinutes - TimeUnit.HOURS.toMinutes(hours);

                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext
                        .buildConstraintViolationWithTemplate(String.format("%s %sh %sm", passwordChangeTimer, hours, minutes))
                        .addConstraintViolation();
                return false;
            }

            if (user != null && !bCryptPasswordEncoder.matches(resetPassword.currentPassword(), user.getPassword())) {
                constraintViolation(constraintValidatorContext, currentPassword);
                return false;
            }
            password = resetPassword.newPassword();
            confirmPassword = resetPassword.confirmPassword();
        } else if (object instanceof ResetPasswordWithToken resetPasswordWithToken) {
            password = resetPasswordWithToken.newPassword();
            confirmPassword = resetPasswordWithToken.confirmPassword();
        }

        if (password.length() < 8 || confirmPassword.length() > 16) {
            constraintViolation(constraintValidatorContext, lengthMessage);
            return false;
        }

        if (!password.matches(".*[A-Z].*")) {
            constraintViolation(constraintValidatorContext, uppercaseMessage);
            return false;
        }

        if (!password.matches(".*[a-z].*")) {
            constraintViolation(constraintValidatorContext, lowercaseMessage);
            return false;
        }

        if (!password.matches(".*[0-9].*")) {
            constraintViolation(constraintValidatorContext, numbersMessage);
            return false;
        }

        if (!password.matches(".*[!@#$%^&*()].*")) {
            constraintViolation(constraintValidatorContext, specialCharacterMessage);
            return false;
        }
        return password.equals(confirmPassword);
    }

    @Override
    public void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        ConstraintViolation.super.constraintViolation(constraintValidatorContext, violation);
    }
}