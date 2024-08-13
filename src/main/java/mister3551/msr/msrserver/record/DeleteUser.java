package mister3551.msr.msrserver.record;

import mister3551.msr.msrserver.security.validator.anno.ValidDeleteUser;

public record DeleteUser(
        @ValidDeleteUser
        String verificationString) {
}