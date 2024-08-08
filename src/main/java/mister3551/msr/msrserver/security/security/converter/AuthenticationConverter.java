package mister3551.msr.msrserver.security.security.converter;

import mister3551.msr.msrserver.security.security.CustomUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthenticationConverter implements Converter<Jwt, AuthenticationToken> {

    @SuppressWarnings("unchecked")
    @Override
    public AuthenticationToken convert(Jwt source) {
        Map<String, String> user = (Map<String, String>) source.getClaims().get("user");

        Object authoritiesClaim = user.get("authorities");
        Set<GrantedAuthority> authorities = ((Collection<String>) authoritiesClaim).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        CustomUser customUser = new CustomUser(
                user.get("fullName"),
                user.get("username"),
                "",
                user.get("emailAddress"),
                authorities,
                user.get("country"),
                Boolean.parseBoolean(String.valueOf(user.get("accountConfirmed"))),
                Boolean.parseBoolean(String.valueOf(user.get("accountLocked"))));
        return new AuthenticationToken(authorities, customUser);
    }
}