package mister3551.msr.msrserver.security.validator.anno;

import com.nimbusds.jose.Payload;
import jakarta.validation.Constraint;
import mister3551.msr.msrserver.security.validator.DeleteUserValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = DeleteUserValidator.class)
@Documented
public @interface ValidDeleteUser {

    String message() default "Validation string must be";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}