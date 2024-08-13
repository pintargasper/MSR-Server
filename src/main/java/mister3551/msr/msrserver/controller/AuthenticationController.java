package mister3551.msr.msrserver.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @PostMapping("/auth/admin")
    public boolean authAdmin(Authentication authentication) {
        return authentication.isAuthenticated() && authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @PostMapping("/auth/user")
    public boolean authUser(Authentication authentication) {
        return authentication.isAuthenticated()
                && (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))
                || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @PostMapping("/auth/public")
    public String authPublic(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                return "ROLE_ADMIN";
            }
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
                return "ROLE_USER";
            }
        }
        return null;
    }
}