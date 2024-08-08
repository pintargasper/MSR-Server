package mister3551.msr.msrserver.security.security.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mister3551.msr.msrserver.security.security.impl.ConstraintViolation;
import mister3551.msr.msrserver.security.security.validator.anno.ValidFullName;
import org.springframework.stereotype.Component;

@Component
public class FullNameValidator implements ConstraintValidator<ValidFullName, String>, ConstraintViolation {

    private String lengthMessage;
    private String numbersMessage;

    @Override
    public void initialize(ValidFullName constraintAnnotation) {
        this.lengthMessage = constraintAnnotation.lengthMessage();
        this.numbersMessage = constraintAnnotation.numbersMessage();
    }

    @Override
    public boolean isValid(String fullName, ConstraintValidatorContext constraintValidatorContext) {

        if (fullName.length() < 3 || fullName.length() > 30) {
            constraintViolation(constraintValidatorContext, lengthMessage);
            return false;
        }

        if (fullName.matches("\\d+")) {
            constraintViolation(constraintValidatorContext, numbersMessage);
            return false;
        }
        return fullName.matches("^[a-zA-ZčćđšžČĆĐŠŽ\\s]+$");
    }

    @Override
    public void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        ConstraintViolation.super.constraintViolation(constraintValidatorContext, violation);
    }
}