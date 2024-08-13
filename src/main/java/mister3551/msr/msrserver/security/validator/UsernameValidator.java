package mister3551.msr.msrserver.security.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mister3551.msr.msrserver.security.entity.User;
import mister3551.msr.msrserver.security.repository.UsersRepository;
import mister3551.msr.msrserver.security.security.CustomUser;
import mister3551.msr.msrserver.security.service.AuthService;
import mister3551.msr.msrserver.security.validator.anno.ValidUsername;
import mister3551.msr.msrserver.security.validator.impl.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UsernameValidator implements ConstraintValidator<ValidUsername, Object>, ConstraintViolation {

    private final AuthService authService;
    private final UsersRepository usersRepository;
    private String lengthMessage;
    private String uppercaseMessage;
    private String specialCharacterMessage;
    private String existsMessage;

    @Autowired
    public UsernameValidator(AuthService authService, UsersRepository usersRepository) {
        this.authService = authService;
        this.usersRepository = usersRepository;
    }

    @Override
    public void initialize(ValidUsername constraintAnnotation) {
        this.lengthMessage = constraintAnnotation.lengthMessage();
        this.uppercaseMessage = constraintAnnotation.uppercaseMessage();
        this.specialCharacterMessage = constraintAnnotation.specialCharactersMessage();
        this.existsMessage = constraintAnnotation.existsMessage();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        String username = object.toString();

        if (username == null) {
            return false;
        }

        Authentication authentication = authService.getAuthentication();
        User user = usersRepository.findByUsername(username);

        if (user != null && authentication.getPrincipal().equals("anonymousUser")) {
            constraintViolation(constraintValidatorContext, existsMessage);
            return false;
        }

        if (authentication.getPrincipal() instanceof CustomUser customUser) {
            if (user != null && !user.getUsername().equals(customUser.getUsername())) {
                constraintViolation(constraintValidatorContext, existsMessage);
                return false;
            }
        }

        if (username.length() < 4 || username.length() > 16) {
            constraintViolation(constraintValidatorContext, lengthMessage);
            return false;
        }

        if (username.matches(".*[A-Z].*")) {
            constraintViolation(constraintValidatorContext, uppercaseMessage);
            return false;
        }

        if (username.matches(".*[!@#$%^&*()_-].*")) {
            constraintViolation(constraintValidatorContext, specialCharacterMessage);
            return false;
        }
        return username.matches("^[a-z0-9]+$");
    }

    @Override
    public void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        ConstraintViolation.super.constraintViolation(constraintValidatorContext, violation);
    }
}