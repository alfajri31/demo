package com.example.demo.service;

import com.example.demo.entity.LoginEntity;
import com.example.demo.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class LoginService implements ILoginService{

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LoginEntity> loginEntity = loginRepository.findByUsername(username);
       if(!loginEntity.isPresent()) {
           throw new UsernameNotFoundException(username);
       }
       else {
           return new User(loginEntity.get().getUsername(),passwordEncoder.encode(loginEntity.get().getPassword()),true,true,true,true,new ArrayList<>());
       }
    }
}
