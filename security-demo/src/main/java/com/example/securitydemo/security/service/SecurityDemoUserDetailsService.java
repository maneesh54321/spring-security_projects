package com.example.securitydemo.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("securityDemoUserDetailsService")
public class SecurityDemoUserDetailsService implements UserDetailsService {

    private final SecurityDemoUserRepository repository;

    @Autowired
    public SecurityDemoUserDetailsService(SecurityDemoUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SecurityDemoUser> maybeUser = repository.findByUsername(username);
        if (maybeUser.isPresent()) {
            SecurityDemoUser user = maybeUser.get();
            return new SecurityDemoUserDetails(
                    user.getUsername(),
                    user.getPassword(),
                    new ArrayList<>(user.getAllRoles()).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
            );
        }
        throw new UsernameNotFoundException(String.format("User with username: '%s' NOT FOUND", username));
    }
}
