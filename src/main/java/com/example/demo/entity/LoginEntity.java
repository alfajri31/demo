package com.example.demo.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "login")
public class LoginEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 50)
    private String username;
    private String password;
    private String token;
    public Long getId() {
        return id;
    }

    @OneToMany(mappedBy = "loginEntity")
    private List<ProductEntity> productEntityList;

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
