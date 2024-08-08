package mister3551.msr.msrserver.security.security.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mister3551.msr.msrserver.security.security.impl.ConstraintViolation;
import mister3551.msr.msrserver.security.security.validator.anno.ValidCountry;
import org.springframework.stereotype.Component;

@Component
public class CountryValidator implements ConstraintValidator<ValidCountry, String>, ConstraintViolation {

    private String numbersMessage;

    @Override
    public void initialize(ValidCountry constraintAnnotation) {
        this.numbersMessage = constraintAnnotation.numbersMessage();
    }

    @Override
    public boolean isValid(String country, ConstraintValidatorContext constraintValidatorContext) {

        if (country.matches(".*[0-9].*")) {
            constraintViolation(constraintValidatorContext, numbersMessage);
            return false;
        }
        return country.matches("^[a-zA-Z\\s]+$");
    }

    @Override
    public void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        ConstraintViolation.super.constraintViolation(constraintValidatorContext, violation);
    }
}