package mister3551.msr.msrserver.security.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import mister3551.msr.msrserver.security.security.converter.AuthenticationConverter;
import mister3551.msr.msrserver.security.security.generator.Jwks;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebMvc
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    private RSAKey rsaKey;

    @Bean
    UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    SecurityFilterChain tokenSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/admin", "/sign-in")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin", "/sign-in").permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configure(http))
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/sign-up").permitAll()

                                .requestMatchers("/get-news").permitAll()
                                .requestMatchers("/news/{title}").permitAll()
                                .requestMatchers("/news/suggestions/{title}").permitAll()

                                .requestMatchers("/images/{path}/{name}").permitAll()

                                .requestMatchers("/countries").permitAll()

                                .requestMatchers("/favicon.ico").permitAll()

                                .requestMatchers("/user").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

                                .requestMatchers("/user/statistics").hasAnyAuthority("ROLE_USER")
                                .requestMatchers("/user/weapon/statistics").hasAnyAuthority("ROLE_USER")

                                .requestMatchers("/user/data").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                                .requestMatchers("/user/update").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                                .requestMatchers("/user/change-password").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                                .requestMatchers("/user/delete").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

                                //Auth
                                .requestMatchers("/auth/admin").hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/auth/user").hasAnyAuthority("ROLE_USER")
                                .requestMatchers("/auth/public").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                                .requestMatchers("/auth/error").permitAll()

                                //Admin
                                .requestMatchers("/dashboard").hasAuthority("ROLE_ADMIN")

                                .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(new AuthenticationConverter())))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, ApplicationEventPublisher applicationEventPublisher) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        ProviderManager providerManager = new ProviderManager(authProvider);
        providerManager.setAuthenticationEventPublisher(authenticationEventPublisher(applicationEventPublisher));
        return providerManager;
    }

    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        rsaKey = Jwks.generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwks) {
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    JwtDecoder jwtDecoder() throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST")
                .allowedHeaders("Origin", "Content-Type", "Authorization")
                .allowCredentials(false)
                .maxAge(3600);
    }
}