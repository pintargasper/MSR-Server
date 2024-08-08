package mister3551.msr.msrserver.security.record;

public record SignInRequest(
        String usernameOrEmailAddress,
        String password) {
}