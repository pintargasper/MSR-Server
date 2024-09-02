package mister3551.msr.msrserver.record;

import mister3551.msr.msrserver.security.validator.anno.ValidPassword;
import mister3551.msr.msrserver.security.validator.anno.ValidResetToken;

@ValidPassword
public record ResetPasswordWithToken(
        String currentPassword,
        String newPassword,
        String confirmPassword,
        @ValidResetToken
        String token) {
}