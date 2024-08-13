package mister3551.msr.msrserver.security.validator.anno;

import com.nimbusds.jose.Payload;
import jakarta.validation.Constraint;
import mister3551.msr.msrserver.security.validator.FullNameValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = FullNameValidator.class)
@Documented
public @interface ValidFullName {

    String message() default "The full name must include only latin letters";
    String lengthMessage() default "Full name must be between 4 and 30 characters long";
    String numbersMessage() default "Full name must not contains numbers";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}