package com.example.demo.entity;

import org.hibernate.engine.profile.Fetch;

import javax.persistence.*;

@Entity
@Table(name = "transaction_detail")
public class TransactionDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 3,name = "document_code")
    private String documentCode;
    @Column(length = 10, name="document_number")
    private String documentNumber;
    @Column(length = 18,name = "product_code")
    private String productCode;
    private Integer price;
    private Integer quantity;
    @Column(length = 5)
    private String unit;
    @Column(name = "sub_total")
    private Integer subTotal;
    @Column(length = 5)
    private String currency;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_header_entity_id")
    private TransactionHeaderEntity transactionHeaderEntity;

    public TransactionHeaderEntity getTransactionHeaderEntity() {
        return transactionHeaderEntity;
    }

    public void setTransactionHeaderEntity(TransactionHeaderEntity transactionHeaderEntity) {
        this.transactionHeaderEntity = transactionHeaderEntity;
    }

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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
