package com.example.demo.config;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OnceRequestPerFilter extends OncePerRequestFilter {

    @Autowired
    private Environment environment;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if(uri.matches(environment.getProperty("login.path")) ||
                uri.equals(environment.getProperty("bootstrap.path")) ||
                uri.equals(environment.getProperty("jquery.path")) ||
                uri.matches(environment.getProperty("h2.path")) ||
                uri.matches(environment.getProperty("product.path")) ||
                uri.matches(environment.getProperty("singleProduct.path")) ||
                uri.matches(environment.getProperty("checkout.path")) ||
                uri.matches(environment.getProperty("order.path")) ||
                uri.matches(environment.getProperty("report.path")) ||
                uri.equals("/")
        ) {
            filterChain.doFilter(request,response);
        }
        else {
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if(authHeader==null) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
            else {
                String jwt = authHeader.replace("Bearer","");
                if(!isJwtValid(jwt)) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                }
                else {
                    filterChain.doFilter(request,response);
                }
            }
        }
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
