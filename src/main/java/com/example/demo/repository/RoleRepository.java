package com.example.demo.repository;

import com.example.demo.entity.LoginEntity;
import com.example.demo.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {

    RoleEntity findByName(String roleName);
}
