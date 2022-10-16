package com.example.demo.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutHandler {



    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        request.removeAttribute(authentication.getName());
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        try {
            redirectStrategy.sendRedirect(request, response, "/login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}