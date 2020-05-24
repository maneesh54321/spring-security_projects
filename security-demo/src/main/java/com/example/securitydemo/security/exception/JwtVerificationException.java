package com.example.securitydemo.security.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtVerificationException extends AuthenticationException {
    public JwtVerificationException(String msg) {
        super(msg);
    }
}
