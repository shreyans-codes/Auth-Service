package com.sheru.Auth.Service.Model;

import jakarta.persistence.*;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
@Builder
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer roleId;

    private String authority;

    public Role(Integer roleId, String authority) {
        this.roleId = roleId;
        this.authority = authority;
    }

    public Role(String authority) {
        this.authority = authority;
    }

    public Role() {
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
