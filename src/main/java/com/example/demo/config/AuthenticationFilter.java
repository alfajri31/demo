package com.example.demo.config;

import com.example.demo.entity.LoginEntity;
import com.example.demo.model.User;
import com.example.demo.repository.LoginRepository;
import com.example.demo.service.ILoginService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private ILoginService iLoginService;

    private LoginRepository loginRepository;


    AuthenticationFilter(ILoginService iLoginService,LoginRepository loginRepository) {
        this.iLoginService = iLoginService;
        this.loginRepository = loginRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username, password;
        username = request.getParameter("username");
        password = request.getParameter("password");
        //user application/json
//        Map<String, String> requestMap= null;
//        try {
//            requestMap = new ObjectMapper().readValue(request.getInputStream(), Map.class);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        username = requestMap.get("username");
//        password = requestMap.get("password");
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(authResult.getPrincipal(),User.class);
        Optional<LoginEntity> userDetails = loginRepository.findByUsername(user.getUsername());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        String token = Jwts.builder().setSubject(String.valueOf(userDetails.get().getUsername())).setExpiration(new Date(System.currentTimeMillis() + Long.parseLong("864000000"))).signWith(SignatureAlgorithm.HS512,"fajrifajri").compact();
        Optional<LoginEntity> loginEntityOptional = loginRepository.findByUsername(user.getUsername());
        loginEntityOptional.get().setToken(token);
        loginRepository.save(loginEntityOptional.get());
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//        HttpSession newSession = request.getSession();
//        newSession.setMaxInactiveInterval(30);
//        request.getSession().setAttribute(loginEntityOptional.get().getUsername(),new Object());
        redirectStrategy.sendRedirect(request, response, "/product");
    }

    @Override
    protected AuthenticationFailureHandler getFailureHandler() {
        return super.getFailureHandler();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        return authenticationManagerBuilder.build();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, "/login");
    }
}
