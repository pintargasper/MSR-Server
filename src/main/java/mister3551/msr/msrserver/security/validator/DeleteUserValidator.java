package mister3551.msr.msrserver.security.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mister3551.msr.msrserver.security.security.CustomUser;
import mister3551.msr.msrserver.security.service.AuthService;
import mister3551.msr.msrserver.security.validator.anno.ValidDeleteUser;
import mister3551.msr.msrserver.security.validator.impl.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class DeleteUserValidator implements ConstraintValidator<ValidDeleteUser, Object>, ConstraintViolation {

    private final AuthService authService;
    private String message;

    @Autowired
    public DeleteUserValidator(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void initialize(ValidDeleteUser constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext){
        String verificationString = object.toString();

        Authentication authentication = authService.getAuthentication();
        CustomUser customUser = (CustomUser) authentication.getPrincipal();

        String valid = String.format("%s%s", customUser.getUsername(), "/delete");
        boolean isValid = verificationString.equals(message);

        if (!isValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(String.format("%s %s", message, valid))
                    .addConstraintViolation();
        }
        return verificationString.equals(String.format("%s%s", customUser.getUsername(), "/delete"));
    }

    @Override
    public void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        ConstraintViolation.super.constraintViolation(constraintValidatorContext, violation);
    }
}