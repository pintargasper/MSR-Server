package mister3551.msr.msrserver.security.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mister3551.msr.msrserver.security.impl.ConstraintViolation;
import mister3551.msr.msrserver.security.validator.anno.ValidBirthdate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class BirthdateValidator implements ConstraintValidator<ValidBirthdate, String>, ConstraintViolation {

    private String nullBirthdate;
    private String characterMessage;
    private String patternMessage;

    @Override
    public void initialize(ValidBirthdate constraintAnnotation) {
        this.nullBirthdate = constraintAnnotation.nullMessage();
        this.characterMessage = constraintAnnotation.characterMessage();
        this.patternMessage = constraintAnnotation.patternMessage();
    }

    @Override
    public boolean isValid(String birthdate, ConstraintValidatorContext constraintValidatorContext) {

        if (birthdate == null) {
            constraintViolation(constraintValidatorContext, nullBirthdate);
            return false;
        }

        if (!birthdate.matches(".*[0-9/].*")) {
            constraintViolation(constraintValidatorContext, characterMessage);
            return false;
        }

        if (!birthdate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            constraintViolation(constraintValidatorContext, patternMessage);
            return false;
        }
        return Period.between(LocalDate.parse(birthdate), LocalDate.now()).getYears() >= 10;
    }

    @Override
    public void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        ConstraintViolation.super.constraintViolation(constraintValidatorContext, violation);
    }
}