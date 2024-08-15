package mister3551.msr.msrserver.service;

import mister3551.msr.msrserver.security.security.CustomUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public boolean authAdmin(Authentication authentication) {
        return authentication.isAuthenticated() && authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    public boolean authUser(Authentication authentication) {
        return authentication.isAuthenticated() && (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    public String authPublic(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            return authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) ? "ROLE_ADMIN" :
                    authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")) ? "ROLE_USER" : null;
        }
        return null;
    }

    public String authError(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            CustomUser customUser = (CustomUser) authentication.getPrincipal();
            if (customUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                return "ROLE_ADMIN";
            }
            if (customUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
                return "ROLE_USER";
            }
        }
        return null;
    }
}