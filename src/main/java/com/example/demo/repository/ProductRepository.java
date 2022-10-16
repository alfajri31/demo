package com.example.demo.repository;

import com.example.demo.entity.LoginEntity;
import com.example.demo.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity,String> {
    List<ProductEntity> findAllByLoginEntity(LoginEntity loginEntity);
}
