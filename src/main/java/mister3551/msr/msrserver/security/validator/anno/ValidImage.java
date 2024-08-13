package mister3551.msr.msrserver.security.validator.anno;

import com.nimbusds.jose.Payload;
import jakarta.validation.Constraint;
import mister3551.msr.msrserver.security.validator.ImageValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ImageValidator.class)
@Documented
public @interface ValidImage {

    String message() default "Wrong image format";
    String lengthMessage() default "Image size is more than 1 MB";
    String charactersMessage() default "Image contains invalid Characters";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}