package com.example.securitydemo.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SecurityDemoCommandLineRunner implements CommandLineRunner {

    private final static Logger logger = LoggerFactory.getLogger(SecurityDemoCommandLineRunner.class);

    private final SecurityDemoUserRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityDemoCommandLineRunner(SecurityDemoUserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        logger.info("Adding users to database!!!");
        SecurityDemoUser user1 = new SecurityDemoUser("admin", passwordEncoder.encode("admin"));
        user1.addRole("admin");
        SecurityDemoUser user2 = new SecurityDemoUser("user", passwordEncoder.encode("user"));
        user2.addRole("user");

        repository.save(user1);
        repository.save(user2);
    }
}
