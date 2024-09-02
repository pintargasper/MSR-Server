package mister3551.msr.msrserver.record;

import mister3551.msr.msrserver.security.validator.anno.ValidResetPassword;

public record EmailAddress(
        @ValidResetPassword
        String emailAddress) {
}