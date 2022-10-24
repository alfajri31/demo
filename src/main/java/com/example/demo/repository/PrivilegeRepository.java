package com.example.demo.repository;

import com.example.demo.entity.LoginEntity;
import com.example.demo.entity.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity,Long>  {
}
