package mister3551.msr.msrserver.security.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mister3551.msr.msrserver.security.record.SignInGoogleRequest;
import mister3551.msr.msrserver.security.validator.anno.ValidGoogleSignIn;
import mister3551.msr.msrserver.security.validator.impl.ConstraintViolation;
import org.springframework.stereotype.Component;

@Component
public class GoogleSignInValidator implements ConstraintValidator<ValidGoogleSignIn, SignInGoogleRequest>, ConstraintViolation {

    private String message;
    private String issuerMessage;
    private String emailVerifiedMessage;
    private String expiredTokenMessage;
    private String notYetValidMessage;
    private String tokenIdMessage;

    @Override
    public void initialize(ValidGoogleSignIn constraintAnnotation) {
        this.message = constraintAnnotation.message();
        this.issuerMessage = constraintAnnotation.issuerMessage();
        this.emailVerifiedMessage = constraintAnnotation.emailVerifiedMessage();
        this.expiredTokenMessage = constraintAnnotation.expiredTokenMessage();
        this.notYetValidMessage = constraintAnnotation.notYetValidMessage();
        this.tokenIdMessage = constraintAnnotation.tokenIdMessage();
    }

    @Override
    public boolean isValid(SignInGoogleRequest signInGoogleRequest, ConstraintValidatorContext constraintValidatorContext) {
        String expectedAudience = "<Google token>";
        String expectedIssuer = "<Google>";

        if (!signInGoogleRequest.aud().equals(expectedAudience)) {
            constraintViolation(constraintValidatorContext, message);
            return false;
        }

        if (!signInGoogleRequest.iss().equals(expectedIssuer)) {
            constraintViolation(constraintValidatorContext, issuerMessage);
            return false;
        }

        if (!Boolean.parseBoolean(signInGoogleRequest.email_verified())) {
            constraintViolation(constraintValidatorContext, emailVerifiedMessage);
            return false;
        }

        long currentTime = System.currentTimeMillis() / 1000;

        if (Long.parseLong(signInGoogleRequest.exp()) < currentTime) {
            constraintViolation(constraintValidatorContext, expiredTokenMessage);
            return false;
        }

        if (Long.parseLong(signInGoogleRequest.nbf()) > currentTime) {
            constraintViolation(constraintValidatorContext, notYetValidMessage);
            return false;
        }

        String jti = signInGoogleRequest.jti();
        if (jti == null || jti.isEmpty()) {
            constraintViolation(constraintValidatorContext, tokenIdMessage);
            return false;
        }
        return true;
    }

    @Override
    public void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        ConstraintViolation.super.constraintViolation(constraintValidatorContext, violation);
    }
}