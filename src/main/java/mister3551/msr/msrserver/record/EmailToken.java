package mister3551.msr.msrserver.record;

import mister3551.msr.msrserver.security.validator.anno.ValidEmailToken;

public record EmailToken(
        @ValidEmailToken
        String token) {
}