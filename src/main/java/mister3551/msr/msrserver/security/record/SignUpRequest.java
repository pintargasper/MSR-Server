package mister3551.msr.msrserver.security.record;

import mister3551.msr.msrserver.security.security.validator.anno.*;

@ValidPassword
public record SignUpRequest(
        @ValidFullName
        String fullName,
        @ValidUsername
        String username,
        @ValidEmailAddress
        String emailAddress,
        String password,
        String confirmPassword,
        @ValidBirthdate
        String birthdate,
        @ValidCountry
        String country) {
}