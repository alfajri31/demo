package com.example.demo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "login")
public class LoginEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 50)
    private String username;
    private String password;
    private String token;

    @Column(name = "roles_id")
    private Long rolesId;

    @ManyToMany
    @JoinTable(name = "login_roles", joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
            name = "roles_id", referencedColumnName = "id")
    )
    private Collection<RoleEntity> roleEntities;

    public Long getId() {
        return id;
    }

    @OneToMany(mappedBy = "loginEntity")
    private List<ProductEntity> productEntityList;

    public Collection<RoleEntity> getRoleEntities() {
        return roleEntities;
    }

    public void setRoleEntities(Collection<RoleEntity> roleEntities) {
        this.roleEntities = roleEntities;
    }

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

    public Long getRolesId() {
        return rolesId;
    }

    public void setRolesId(Long rolesId) {
        this.rolesId = rolesId;
    }
}
