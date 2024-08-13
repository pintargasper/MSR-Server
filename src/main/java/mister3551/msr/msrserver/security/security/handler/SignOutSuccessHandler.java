package mister3551.msr.msrserver.security.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mister3551.msr.msrserver.security.security.CustomUser;
import mister3551.msr.msrserver.security.security.impl.CustomResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import java.io.IOException;

public class SignOutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements CustomResponse {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                cookie.setMaxAge(0);
                cookie.setValue(null);
                response.addCookie(cookie);
            }
        }

        String redirectUrl = "/sign-in";
        if (authentication != null) {
            CustomUser customUser = (CustomUser) authentication.getPrincipal();
            redirectUrl = customUser.getAuthorities().stream()
                    .filter(a -> a instanceof SimpleGrantedAuthority)
                    .map(authority -> (SimpleGrantedAuthority) authority)
                    .filter(authority -> "ROLE_ADMIN".equals(authority.getAuthority()))
                    .findFirst()
                    .map(authority -> "/admin")
                    .orElse("/sign-in");
        }

        response(response, redirectUrl);
        super.onLogoutSuccess(request, response, authentication);
    }
}