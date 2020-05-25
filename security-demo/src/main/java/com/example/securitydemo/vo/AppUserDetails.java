package com.example.securitydemo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class AppUserDetails {
    private String username;
    private Set<String> roles;
}
