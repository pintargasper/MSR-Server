package mister3551.msr.msrserver.security.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import mister3551.msr.msrserver.security.record.SignInGoogleRequest;
import mister3551.msr.msrserver.security.record.SignInRequest;
import mister3551.msr.msrserver.security.record.SignUpRequest;
import mister3551.msr.msrserver.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/admin")
    public String adminSignIn(@RequestBody SignInRequest signInRequest) {
        return authService.adminSignIn(signInRequest);
    }

    @PostMapping("/sign-in")
    public String[] signIn(@RequestBody SignInRequest signInRequest) {
        return authService.signIn(signInRequest);
    }

    @PostMapping("/sign-in-google")
    public String[] signInGoogle(@Valid @RequestBody SignInGoogleRequest signInGoogleRequest) {
        return authService.signInGoogle(signInGoogleRequest);
    }

    @PostMapping("/sign-up")
    public String[] signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return authService.signUp(signUpRequest);
    }

    @PostMapping("/sign-out")
    public void signOut(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws ServletException, IOException {
        authService.signOut(httpServletRequest, httpServletResponse, authentication);
    }
}