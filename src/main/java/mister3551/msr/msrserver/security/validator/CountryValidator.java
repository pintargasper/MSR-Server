package mister3551.msr.msrserver.security.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mister3551.msr.msrserver.entity.Country;
import mister3551.msr.msrserver.repository.CountryRepository;
import mister3551.msr.msrserver.security.validator.anno.ValidCountry;
import mister3551.msr.msrserver.security.validator.impl.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CountryValidator implements ConstraintValidator<ValidCountry, Object>, ConstraintViolation {

    private final CountryRepository countryRepository;
    private String numbersMessage;

    @Autowired
    public CountryValidator(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public void initialize(ValidCountry constraintAnnotation) {
        this.numbersMessage = constraintAnnotation.numbersMessage();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        String countryName = (String) object;

        if (countryName.matches(".*[0-9].*")) {
            constraintViolation(constraintValidatorContext, numbersMessage);
            return false;
        }

        Country country = countryRepository.findByName(countryName);

        if (country == null) {
            return false;
        }

        return countryName.matches("^[a-zA-Z\\s]+$");
    }

    @Override
    public void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        ConstraintViolation.super.constraintViolation(constraintValidatorContext, violation);
    }
}