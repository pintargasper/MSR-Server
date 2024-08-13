package mister3551.msr.msrserver.record;

import mister3551.msr.msrserver.security.validator.anno.ValidPassword;

@ValidPassword
public record ResetPassword(
        String currentPassword,
        String newPassword,
        String confirmPassword) {
}