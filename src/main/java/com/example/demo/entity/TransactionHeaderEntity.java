package com.example.demo.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transaction_header")
public class TransactionHeaderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 3,name = "document_code")
    private String documentCode;
    @Column(length = 10,name="document_number")
    private String documentNumber;
    private String username;
    private Integer total;
    private Date date;

    @OneToOne(mappedBy = "transactionHeaderEntity")
    private TransactionDetailEntity transactionDetailEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TransactionDetailEntity getTransactionDetailEntity() {
        return transactionDetailEntity;
    }

    public void setTransactionDetailEntity(TransactionDetailEntity transactionDetailEntity) {
        this.transactionDetailEntity = transactionDetailEntity;
    }
}
