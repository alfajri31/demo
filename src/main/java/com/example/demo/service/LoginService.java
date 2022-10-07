package com.example.demo.service;

import com.example.demo.entity.LoginEntity;
import com.example.demo.repository.LoginRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class LoginService implements ILoginService{

    @Autowired
    private LoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LoginEntity> loginEntity = loginRepository.findByUsername(username);
       if(!loginEntity.isPresent()) {
           throw new UsernameNotFoundException(username);
       }
       else {
           loginEntity.get().setUsername(loginEntity.get().getUsername());
           loginEntity.get().setPassword(loginEntity.get().getPassword());
           loginRepository.save(loginEntity.get());
           return new User(loginEntity.get().getUsername(),loginEntity.get().getPassword(),true,true,true,true,new ArrayList<>());
       }
    }
}
