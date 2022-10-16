package com.example.demo.config;

import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint  implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String jwt = authHeader.replace("Bearer","");
        if(!isJwtValid(jwt)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
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
