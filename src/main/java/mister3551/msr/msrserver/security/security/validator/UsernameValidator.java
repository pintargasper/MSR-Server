package mister3551.msr.msrserver.security.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mister3551.msr.msrserver.security.impl.ConstraintViolation;
import mister3551.msr.msrserver.security.validator.anno.ValidUsername;
import org.springframework.stereotype.Component;

@Component
public class UsernameValidator implements ConstraintValidator<ValidUsername, String>, ConstraintViolation {

    private String lengthMessage;
    private String uppercaseMessage;
    private String specialCharacterMessage;

    @Override
    public void initialize(ValidUsername constraintAnnotation) {
        this.lengthMessage = constraintAnnotation.lengthMessage();
        this.uppercaseMessage = constraintAnnotation.uppercaseMessage();
        this.specialCharacterMessage = constraintAnnotation.specialCharactersMessage();
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {

        if (username.length() < 4 || username.length() > 16) {
            constraintViolation(constraintValidatorContext, lengthMessage);
            return false;
        }

        if (username.matches(".*[A-Z].*")) {
            constraintViolation(constraintValidatorContext, uppercaseMessage);
            return false;
        }

        if (username.matches(".*[!@#$%^&*()].*")) {
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