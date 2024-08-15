package mister3551.msr.msrserver.controller;

import mister3551.msr.msrserver.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/auth/admin")
    public boolean authAdmin(Authentication authentication) {
        return authenticationService.authAdmin(authentication);
    }

    @PostMapping("/auth/user")
    public boolean authUser(Authentication authentication) {
        return authenticationService.authUser(authentication);
    }

    @PostMapping("/auth/public")
    public String authPublic(Authentication authentication) {
        return authenticationService.authPublic(authentication);
    }

    @PostMapping("/auth/error")
    public String authError(Authentication authentication) {
        return authenticationService.authError(authentication);
    }
}