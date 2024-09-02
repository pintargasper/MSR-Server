package mister3551.msr.msrserver.security.validator.anno;

import com.nimbusds.jose.Payload;
import jakarta.validation.Constraint;
import mister3551.msr.msrserver.security.validator.EmailResetTokenValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailResetTokenValidator.class)
@Documented
public @interface ValidResetToken {

    String message() default "Something went wrong";
    String nonExistsMessage() default "Email address does not exists";
    String invalidTokenMessage() default "Invalid reset token";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}