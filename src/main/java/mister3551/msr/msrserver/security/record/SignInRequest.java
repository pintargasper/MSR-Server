package mister3551.msr.msrserver.record;

public record SignInRequest(
        String usernameOrEmailAddress,
        String password) {
}