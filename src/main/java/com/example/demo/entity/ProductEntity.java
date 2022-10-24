package com.example.demo.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class ProductEntity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Id
    @Column(length = 18)
    private String productCode;
    @Column(length = 30)
    private String productName;
    private Integer price;
    @Column(length = 5)
    private String currency;

    private Integer discount;
    @Column(length = 50)
    private String dimension;
    @Column(length = 5)
    private String unit;
    private Integer quantity;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "productEntity")
    private List<CheckoutEntity> checkoutEntity;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",referencedColumnName = "id")
    private LoginEntity loginEntity;

    public LoginEntity getLoginEntity() {
        return loginEntity;
    }

    public void setLoginEntity(LoginEntity loginEntity) {
        this.loginEntity = loginEntity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public List<CheckoutEntity> getCheckoutEntity() {
        return checkoutEntity;
    }

    public void setCheckoutEntity(List<CheckoutEntity> checkoutEntity) {
        this.checkoutEntity = checkoutEntity;
    }
}
