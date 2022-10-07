package com.example.demo.config;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@Component
public class OnceRequestPerFilter extends OncePerRequestFilter {

    @Autowired
    private Environment environment;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String uri = request.getRequestURI();
        if(uri.matches(environment.getProperty("login.path")) ||
                uri.equals(environment.getProperty("bootstrap.path")) ||
                uri.equals(environment.getProperty("jquery.path")) ||
                uri.matches(environment.getProperty("h2.path")) ||  uri.equals("") || uri.equals("/")
        ) {
            filterChain.doFilter(request,response);
        }
        else {
            HttpSession session = request.getSession(true);
//            if(authHeader==null) {
//                response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            }
//            else {
//                String jwt = authHeader.replace("Bearer","");
                Object jwt = session.getAttribute("token");
                if(!isJwtValid(jwt.toString())) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                }
                else {
                    filterChain.doFilter(request,response);
                }
            }
//        }
    }

    private Boolean isJwtValid(String jwt) {
        Boolean returnValue = true;
        String subject = Jwts.parser().setSigningKey("fajrifajri").parseClaimsJws(jwt).getBody().getSubject();
        if(subject==null || subject.isEmpty()) {
            returnValue = false;
        }
        return returnValue;
    }
}
