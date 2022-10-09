package com.example.demo.repository;

import com.example.demo.entity.CheckoutEntity;
import com.example.demo.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CheckoutRepository extends JpaRepository<CheckoutEntity,Long> {
    List<CheckoutEntity> findAllByToken(String token);

    List<CheckoutEntity> findAllByTokenAndStatusIsNull(String token);
    Optional<CheckoutEntity> findByToken(String token);

    Optional<CheckoutEntity> findByTokenAndProductCode(String token,String productCode);
    Optional<CheckoutEntity> findByTokenAndProductCodeAndStatusIsNull(String token,String productCode);
}
