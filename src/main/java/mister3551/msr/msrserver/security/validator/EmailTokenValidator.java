package mister3551.msr.msrserver.security.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mister3551.msr.msrserver.security.entity.User;
import mister3551.msr.msrserver.security.repository.UsersRepository;
import mister3551.msr.msrserver.security.security.CustomUser;
import mister3551.msr.msrserver.security.service.AuthService;
import mister3551.msr.msrserver.security.validator.anno.ValidEmailToken;
import mister3551.msr.msrserver.security.validator.impl.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class EmailTokenValidator implements ConstraintValidator<ValidEmailToken, Object>, ConstraintViolation {

    private final AuthService authService;
    private final UsersRepository usersRepository;
    private String nonExistsMessage;
    private String alreadyConfirmedMessage;
    private String invalidTokenMessage;

    @Autowired
    public EmailTokenValidator(AuthService authService, UsersRepository usersRepository) {
        this.authService = authService;
        this.usersRepository = usersRepository;
    }

    @Override
    public void initialize(ValidEmailToken constraintAnnotation) {
        this.nonExistsMessage = constraintAnnotation.nonExistsMessage();
        this.alreadyConfirmedMessage = constraintAnnotation.alreadyConfirmedMessage();
        this.invalidTokenMessage = constraintAnnotation.invalidTokenMessage();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext){
        String emailToken = object.toString();

        if (emailToken == null) {
            return false;
        }

        Authentication authentication = authService.getAuthentication();
        CustomUser customUser = (CustomUser) authentication.getPrincipal();

        User user = usersRepository.findByEmailAddress(customUser.getEmailAddress());

        if (user == null) {
            constraintViolation(constraintValidatorContext, nonExistsMessage);
            return false;
        }

        if (user.isAccountConfirmed()) {
            constraintViolation(constraintValidatorContext, alreadyConfirmedMessage);
            return false;
        }

        if (!user.getEmailToken().equals(emailToken)) {
            constraintViolation(constraintValidatorContext, invalidTokenMessage);
            return false;
        }
        return true;
    }

    @Override
    public void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        ConstraintViolation.super.constraintViolation(constraintValidatorContext, violation);
    }
}