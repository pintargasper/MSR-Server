package mister3551.msr.msrserver.record;

import mister3551.msr.msrserver.security.validator.anno.*;
import org.springframework.web.multipart.MultipartFile;

public record UpdateUser(
        @ValidFullName
        String fullName,
        @ValidUsername
        String username,
        @ValidEmailAddress
        String emailAddress,
        @ValidBirthdate
        String birthdate,
        @ValidCountry
        String country,
        @ValidImage
        MultipartFile image) {
}