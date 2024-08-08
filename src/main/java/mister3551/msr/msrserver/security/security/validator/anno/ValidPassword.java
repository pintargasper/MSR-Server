package mister3551.msr.msrserver.security.validator.anno;

import com.nimbusds.jose.Payload;
import jakarta.validation.Constraint;
import mister3551.msr.msrserver.security.validator.PasswordValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface ValidPassword {

    String message() default "Passwords do not match";
    String lengthMessage() default "Password must be between 8 and 16 characters long";
    String uppercaseMessage() default "Password must contain at least one uppercase letter";
    String lowercaseMessage() default "Password must contain at least one lowercase letter";
    String numbersMessage() default "Password must contain at least one number";
    String specialCharacterMessage() default "Password must contain at least one special character (!, @, #, $, %, ^, &, *, ())";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}