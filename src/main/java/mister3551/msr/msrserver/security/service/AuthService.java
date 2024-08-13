package mister3551.msr.msrserver.security.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mister3551.msr.msrserver.security.entity.User;
import mister3551.msr.msrserver.security.record.SignInRequest;
import mister3551.msr.msrserver.security.record.SignUpRequest;
import mister3551.msr.msrserver.security.repository.UsersRepository;
import mister3551.msr.msrserver.security.security.CustomUser;
import mister3551.msr.msrserver.security.security.handler.SignOutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthService {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UsersRepository usersRepository;
    private final SignOutSuccessHandler signOutSuccessHandler;

    @Autowired
    public AuthService(TokenService tokenService, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder, UsersRepository userRepository) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.usersRepository = userRepository;
        this.signOutSuccessHandler = new SignOutSuccessHandler();
    }

    public String adminSignIn(SignInRequest signInRequest) {
        String token;

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequest.usernameOrEmailAddress(), signInRequest.password())
            );

            CustomUser userDetails = (CustomUser) authentication.getPrincipal();

            boolean hasAdminRole = userDetails.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

            if (!hasAdminRole) {
                return "No privileges";
            }

            token = tokenService.generateToken(authentication);

        } catch (BadCredentialsException badCredentialsException) {
            return "Bad Credentials";
        }

        if (token == null) {
            return "Invalid";
        }

        return token;
    }

    public String signIn(SignInRequest signInRequest) {
        String token;

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequest.usernameOrEmailAddress(), signInRequest.password())
            );

            token = tokenService.generateToken(authentication);

            CustomUser userDetails = (CustomUser) authentication.getPrincipal();
            if (!userDetails.isAccountConfirmed()) {
                return "Email address is not confirmed";
            }

            if (userDetails.isAccountLocked()) {
                return "Account is Locked";
            }

        } catch (BadCredentialsException badCredentialsException) {
            return "Bad Credentials";
        }
        return token;
    }

    public String signUp(SignUpRequest signUpRequest) {

        User userByUsername = usersRepository.findByUsername(signUpRequest.username());
        User userByEmail = usersRepository.findByEmailAddress(signUpRequest.emailAddress());

        if (userByUsername != null) {
            return "Username already exists";
        }

        if (userByEmail != null) {
            return "Email address already exists";
        }

        String generatedToken = tokenService.generateEmailToken(signUpRequest, "CONFIRM_EMAIL");

        if (usersRepository.insertUser(
                signUpRequest.fullName(),
                signUpRequest.username(),
                signUpRequest.emailAddress(),
                bCryptPasswordEncoder.encode(signUpRequest.password()),
                signUpRequest.birthdate(),
                signUpRequest.country(),
                generatedToken) == 1) {
            if (usersRepository.insertUserRole(signUpRequest.username()) == 1) {
                return "User successfully created";
            } else {
                usersRepository.deleteUser(signUpRequest.username(), signUpRequest.emailAddress());
                return "Something went wrong";
            }
        }
        return "Something went wrong";
    }

    public void signOut(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws ServletException, IOException {
        signOutSuccessHandler.onLogoutSuccess(httpServletRequest, httpServletResponse, authentication);
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}