package mister3551.msr.msrserver.security.record;

import mister3551.msr.msrserver.security.validator.anno.*;

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
        String image,
        @ValidBirthdate
        String birthdate,
        @ValidCountry
        String country,
        int email_verified) {
}