package com.example.securitydemo.security.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface SecurityDemoUserRepository extends JpaRepository<SecurityDemoUser, Integer> {
    Optional<SecurityDemoUser> findByUsername(String username);
}
