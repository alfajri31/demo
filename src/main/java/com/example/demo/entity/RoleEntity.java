package com.example.demo.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @ManyToMany(mappedBy = "roleEntities")
    private Collection<LoginEntity> logins;

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private Collection<PrivilegeEntity> privileges;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Collection<PrivilegeEntity> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<PrivilegeEntity> privileges) {
        this.privileges = privileges;
    }

    public Collection<LoginEntity> getLogins() {
        return logins;
    }

    public void setLogins(Collection<LoginEntity> logins) {
        this.logins = logins;
    }
}
