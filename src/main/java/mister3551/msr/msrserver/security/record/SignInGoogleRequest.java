package mister3551.msr.msrserver.security.record;

import mister3551.msr.msrserver.security.validator.anno.ValidGoogleSignIn;

@ValidGoogleSignIn
public record SignInGoogleRequest(
        String aud,
        String azp,
        String email,
        String email_verified,
        String exp,
        String family_name,
        String given_name,
        String iat,
        String iss,
        String jti,
        String name,
        String nbf,
        String picture,
        String sub) {
}