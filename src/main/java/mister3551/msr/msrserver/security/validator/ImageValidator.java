package mister3551.msr.msrserver.security.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mister3551.msr.msrserver.security.validator.anno.ValidImage;
import mister3551.msr.msrserver.security.validator.impl.ConstraintViolation;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Component
public class ImageValidator implements ConstraintValidator<ValidImage, Object>, ConstraintViolation {

    private String lengthMessage;
    private String charactersMessage;
    
    private  final String[] imageExtensions = {"jpg", "jpeg", "png", "gif"};

    @Override
    public void initialize(ValidImage constraintAnnotation) {
        this.lengthMessage = constraintAnnotation.lengthMessage();
        this.charactersMessage = constraintAnnotation.charactersMessage();
    }

    @Override
    public boolean isValid(Object Object, ConstraintValidatorContext constraintValidatorContext) {
        MultipartFile multipartFile = (MultipartFile) Object;

        if (multipartFile == null || multipartFile.isEmpty()) {
            return true;
        }

        if (multipartFile.getSize() > 1048576L) {
            constraintViolation(constraintValidatorContext, lengthMessage);
            return false;
        }

        if (Objects.requireNonNull(multipartFile.getOriginalFilename()).contains("..")) {
            constraintViolation(constraintValidatorContext, charactersMessage);
            return false;
        }
        return imageExtensions(getImageExtension(multipartFile.getOriginalFilename()));
    }

    @Override
    public void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        ConstraintViolation.super.constraintViolation(constraintValidatorContext, violation);
    }

    private boolean imageExtensions(String imageExtension) {
        for (String extension : imageExtensions) {
            if (extension.matches(imageExtension)) {
                return true;
            }
        }
        return false;
    }

    public String getImageExtension(String imageName) {
        if (imageName == null) {
            return null;
        }
        String[] fileNameParts = imageName.split("\\.");
        return fileNameParts[fileNameParts.length - 1];
    }
}