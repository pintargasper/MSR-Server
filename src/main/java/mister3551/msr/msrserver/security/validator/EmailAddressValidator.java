package mister3551.msr.msrserver.security.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mister3551.msr.msrserver.security.entity.User;
import mister3551.msr.msrserver.security.repository.UsersRepository;
import mister3551.msr.msrserver.security.security.CustomUser;
import mister3551.msr.msrserver.security.service.AuthService;
import mister3551.msr.msrserver.security.validator.anno.ValidEmailAddress;
import mister3551.msr.msrserver.security.validator.impl.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class EmailAddressValidator implements ConstraintValidator<ValidEmailAddress, Object>, ConstraintViolation {

    private final UsersRepository usersRepository;
    private final AuthService authService;
    private String existsMessage;

    @Autowired
    public EmailAddressValidator(UsersRepository usersRepository, AuthService authService) {
        this.usersRepository = usersRepository;
        this.authService = authService;
    }

    @Override
    public void initialize(ValidEmailAddress constraintAnnotation) {
        this.existsMessage = constraintAnnotation.existsMessage();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext){
        String emailAddress = object.toString();

        if (emailAddress == null) {
            return false;
        }

        Authentication authentication = authService.getAuthentication();
        User user = usersRepository.findByEmailAddress(emailAddress);

        if (user != null && authentication.getPrincipal().equals("anonymousUser")) {
            constraintViolation(constraintValidatorContext, existsMessage);
            return false;
        }

        if (authentication.getPrincipal() instanceof CustomUser customUser) {
            if (user != null && !user.getEmailAddress().equals(customUser.getEmailAddress())) {
                constraintViolation(constraintValidatorContext, existsMessage);
                return false;
            }
        }
        return emailAddress.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    @Override
    public void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        ConstraintViolation.super.constraintViolation(constraintValidatorContext, violation);
    }
}