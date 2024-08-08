package mister3551.msr.msrserver.security.converter;

import mister3551.msr.msrserver.security.CustomUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.util.Collection;

public class AuthenticationToken extends AbstractAuthenticationToken {

    @Serial
    private static final long serialVersionUID = 1L;
    private final CustomUser customUser;

    public AuthenticationToken(Collection<? extends GrantedAuthority> authorities, CustomUser customUser) {
        super(authorities);
        this.customUser = customUser;
    }

    @Override
    public boolean isAuthenticated() {
        return !customUser.getAuthorities().isEmpty();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public String getName() {
        return customUser.getUsername();
    }

    @Override
    public Object getPrincipal() {
        return customUser;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return customUser.getAuthorities();
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        throw new IllegalArgumentException("Don't do this");
    }

    @Override
    public Object getDetails() {
        return customUser.getUsername();
    }
}