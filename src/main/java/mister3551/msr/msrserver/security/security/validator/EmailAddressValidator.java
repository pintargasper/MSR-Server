package mister3551.msr.msrserver.security.security.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mister3551.msr.msrserver.security.security.impl.ConstraintViolation;
import mister3551.msr.msrserver.security.security.validator.anno.ValidEmailAddress;
import org.springframework.stereotype.Component;

@Component
public class EmailAddressValidator implements ConstraintValidator<ValidEmailAddress, String>, ConstraintViolation {

    @Override
    public void initialize(ValidEmailAddress constraintAnnotation) {
    }

    @Override
    public boolean isValid(String emailAddress, ConstraintValidatorContext constraintValidatorContext){
        return emailAddress.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    @Override
    public void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        ConstraintViolation.super.constraintViolation(constraintValidatorContext, violation);
    }
}