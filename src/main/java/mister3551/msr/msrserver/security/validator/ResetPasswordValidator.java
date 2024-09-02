package mister3551.msr.msrserver.security.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mister3551.msr.msrserver.repository.UserRepository;
import mister3551.msr.msrserver.security.entity.User;
import mister3551.msr.msrserver.security.repository.UsersRepository;
import mister3551.msr.msrserver.security.validator.anno.ValidResetPassword;
import mister3551.msr.msrserver.security.validator.impl.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ResetPasswordValidator implements ConstraintValidator<ValidResetPassword, Object>, ConstraintViolation {

    private final UsersRepository usersRepository;
    private final UserRepository userRepository;
    private String nonExistsMessage;
    private String limitMessage;

    @Autowired
    public ResetPasswordValidator(UsersRepository usersRepository, UserRepository userRepository) {
        this.usersRepository = usersRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(ValidResetPassword constraintAnnotation) {
        this.nonExistsMessage = constraintAnnotation.nonExistsMessage();
        this.limitMessage = constraintAnnotation.limitMessage();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext){
        String emailAddress = object.toString();

        if (emailAddress == null) {
            return false;
        }

        User user = usersRepository.findByEmailAddress(emailAddress);

        if (user == null) {
            constraintViolation(constraintValidatorContext, nonExistsMessage);
            return false;
        }

        LocalDateTime passwordTimer = user.getPasswordResetTimer();

        if (passwordTimer != null && Duration.between(passwordTimer, LocalDateTime.now()).compareTo(Duration.ofMinutes(30L)) <= 0 && passwordTimer.isAfter(LocalDateTime.now())) {

            Duration duration = Duration.between(LocalDateTime.now(), passwordTimer);

            Long hours = duration.toHours();
            Long minutes = duration.toMinutes() % 60;

            constraintViolation(constraintValidatorContext, String.format("%s: %sh %sm", limitMessage, hours, minutes));
            return false;
        } else {
            userRepository.updatePasswordResetTimer(user.getUsername(), user.getEmailAddress());
        }
        return true;
    }

    @Override
    public void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        ConstraintViolation.super.constraintViolation(constraintValidatorContext, violation);
    }
}