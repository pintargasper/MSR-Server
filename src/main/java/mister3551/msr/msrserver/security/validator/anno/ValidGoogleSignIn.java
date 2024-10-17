package mister3551.msr.msrserver.security.validator.anno;

import com.nimbusds.jose.Payload;
import jakarta.validation.Constraint;
import mister3551.msr.msrserver.security.validator.GoogleSignInValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = GoogleSignInValidator.class)
@Documented
public @interface ValidGoogleSignIn {

    String message() default "Invalid audience";
    String issuerMessage() default "Invalid issuer";
    String emailVerifiedMessage() default "Email not verified";
    String expiredTokenMessage() default "Token has expired";
    String notYetValidMessage() default "Token is not yet valid";
    String tokenIdMessage() default "Invalid token ID";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}