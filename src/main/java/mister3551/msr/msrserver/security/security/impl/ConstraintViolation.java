package mister3551.msr.msrserver.security.security.impl;

import jakarta.validation.ConstraintValidatorContext;

public interface ConstraintViolation {

    default void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(violation).addConstraintViolation();
    }
}