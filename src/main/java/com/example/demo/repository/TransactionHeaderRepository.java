package com.example.demo.repository;

import com.example.demo.entity.TransactionHeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHeaderRepository extends JpaRepository<TransactionHeaderEntity,Long> {
}
