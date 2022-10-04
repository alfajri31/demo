package com.example.demo.repository;

import com.example.demo.entity.TransactionDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetailEntity,Long> {
}
