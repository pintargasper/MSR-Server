package mister3551.msr.msrserver.security.security.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mister3551.msr.msrserver.security.record.SignUpRequest;
import mister3551.msr.msrserver.security.security.impl.ConstraintViolation;
import mister3551.msr.msrserver.security.security.validator.anno.ValidPassword;

public class PasswordValidator implements ConstraintValidator<ValidPassword, Object>, ConstraintViolation {

    private String lengthMessage;
    private String uppercaseMessage;
    private String lowercaseMessage;
    private String numbersMessage;
    private String specialCharacterMessage;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        this.lengthMessage = constraintAnnotation.lengthMessage();
        this.uppercaseMessage = constraintAnnotation.uppercaseMessage();
        this.lowercaseMessage = constraintAnnotation.lowercaseMessage();
        this.numbersMessage = constraintAnnotation.numbersMessage();
        this.specialCharacterMessage = constraintAnnotation.specialCharacterMessage();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext){

        SignUpRequest signUpRequest = (SignUpRequest) object;

        if (signUpRequest.password().length() < 8 || signUpRequest.password().length() > 16) {
            constraintViolation(constraintValidatorContext, lengthMessage);
            return false;
        }

        if (!signUpRequest.password().matches(".*[A-Z].*")) {
            constraintViolation(constraintValidatorContext, uppercaseMessage);
            return false;
        }

        if (!signUpRequest.password().matches(".*[a-z].*")) {
            constraintViolation(constraintValidatorContext, lowercaseMessage);
            return false;
        }

        if (!signUpRequest.password().matches(".*[0-9].*")) {
            constraintViolation(constraintValidatorContext, numbersMessage);
            return false;
        }

        if (!signUpRequest.password().matches(".*[!@#$%^&*()].*")) {
            constraintViolation(constraintValidatorContext, specialCharacterMessage);
            return false;
        }
        return signUpRequest.password().equals(signUpRequest.confirmPassword());
    }

    @Override
    public void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        ConstraintViolation.super.constraintViolation(constraintValidatorContext, violation);
    }
}