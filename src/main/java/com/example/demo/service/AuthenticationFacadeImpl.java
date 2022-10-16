package com.example.demo.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationFacadeImpl implements IAuthenticationFacade{
    @Override
    public Authentication getAuthentication() {
        SecurityContextHolder.getContext();
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
