package mister3551.msr.msrserver.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mister3551.msr.msrserver.security.impl.CustomResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import java.io.IOException;

public class SignOutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements CustomResponse {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        HttpSession httpSession = httpServletRequest.getSession(true);
        if (httpSession != null) {
            httpSession.invalidate();
        }

        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                httpServletResponse.addCookie(cookie);
            }
        }

        response(httpServletResponse, "/sign-in");
        super.onLogoutSuccess(httpServletRequest, httpServletResponse, authentication);
    }
}