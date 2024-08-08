package mister3551.msr.msrserver.security.security.validator.anno;

import com.nimbusds.jose.Payload;
import jakarta.validation.Constraint;
import mister3551.msr.msrserver.security.security.validator.BirthdateValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = BirthdateValidator.class)
@Documented
public @interface ValidBirthdate {

    String message() default "User must be at least 10 years old";
    String nullMessage() default "Birthdate must not be null";
    String characterMessage() default "Birthdate must not contains characters";
    String patternMessage() default "Birthdate is in wrong format";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}