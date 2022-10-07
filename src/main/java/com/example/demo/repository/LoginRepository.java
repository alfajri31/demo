package com.example.demo.repository;

import com.example.demo.entity.LoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<LoginEntity,Long> {
    Optional<LoginEntity> findByUsernameAndPassword(String username, String password);
    Optional<LoginEntity> findByUsername(String username);
}
