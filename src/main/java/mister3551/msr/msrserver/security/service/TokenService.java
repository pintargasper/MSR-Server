package mister3551.msr.msrserver.security.service;

import mister3551.msr.msrserver.security.record.SignUpRequest;
import mister3551.msr.msrserver.security.security.CustomUser;
import mister3551.msr.msrserver.security.security.record.JwtUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private final JwtEncoder encoder;
    private final String issuer = "Memo Stick Rescue";

    public TokenService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String generateToken(Authentication authentication) {
        Set<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        CustomUser customUser = (CustomUser) authentication.getPrincipal();

        JwtUser jwtUser = new JwtUser(
                customUser.getFullName(),
                authentication.getName(),
                customUser.getEmailAddress(),
                customUser.getCountry(),
                customUser.isAccountConfirmed(),
                customUser.isAccountLocked(),
                authorities
        );

        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plus(1L, ChronoUnit.DAYS))
                .subject(authentication.getName())
                .claim("authorities", authorities)
                .claim("user", jwtUser)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generateEmailToken(SignUpRequest signUpRequest, String type) {

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(type));

        Set<String> authorities = new HashSet<>();
        for (GrantedAuthority authority : grantedAuthorities) {
            authorities.add(authority.getAuthority());
        }

        JwtUser jwtUser = new JwtUser(
                signUpRequest.fullName(),
                signUpRequest.username(),
                signUpRequest.emailAddress(),
                signUpRequest.country(),
                false,
                false,
                authorities
        );

        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plus(1L, ChronoUnit.DAYS))
                .subject(type)
                .claim("authorities", authorities)
                .claim("user", jwtUser)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}