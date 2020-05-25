package com.example.securitydemo.service.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
class SecurityDemoUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String roles;

    public SecurityDemoUser() {
    }

    public SecurityDemoUser(String username, String password) {
        this.username = username;
        this.password = password;
        this.addRole("user");
    }

    public Set<String> getAllRoles() {
        if (StringUtils.isEmpty(roles)) {
            return Collections.emptySet();
        }
        return new HashSet<>(Arrays.asList(roles.split(",")));
    }

    public void addRole(String role) {
        role = "ROLE_" + role.toUpperCase();
        if (StringUtils.isEmpty(roles)) {
            roles = role;
        } else if (!getAllRoles().contains(role)) {
            roles += "," + role;
        }
    }
}
