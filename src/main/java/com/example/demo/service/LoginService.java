package com.example.demo.service;

import com.example.demo.entity.LoginEntity;
import com.example.demo.entity.PrivilegeEntity;
import com.example.demo.entity.RoleEntity;
import com.example.demo.repository.LoginRepository;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class LoginService implements ILoginService{

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LoginEntity> loginEntity = loginRepository.findByUsername(username);
       if(!loginEntity.isPresent()) {
           throw new UsernameNotFoundException(username);
       }
       else {
           if(loginEntity.get().getRolesId()==2L) {
               return new User(loginEntity.get().getUsername(),passwordEncoder.encode(loginEntity.get().getPassword()),true,true,true,true, getAuthorities(Arrays.asList(
                       roleRepository.findByName("ROLE_ADMIN"))));
           }
           else {
               return new User(loginEntity.get().getUsername(),passwordEncoder.encode(loginEntity.get().getPassword()),true,true,true,true, getAuthorities(Arrays.asList(
                       roleRepository.findByName("ROLE_USER"))));
           }
       }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<RoleEntity> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<RoleEntity> roles) {

        List<String> privileges = new ArrayList<>();
        List<PrivilegeEntity> collection = new ArrayList<>();
        for (RoleEntity role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (PrivilegeEntity item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
