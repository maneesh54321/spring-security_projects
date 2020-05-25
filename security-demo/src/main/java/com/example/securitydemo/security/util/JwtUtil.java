package com.example.securitydemo.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.securitydemo.security.SecurityConstants;
import com.example.securitydemo.service.user.SecurityDemoUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;

public class JwtUtil {

    public static String generateToken(Authentication auth) {
        SecurityDemoUserDetails securityDemoUserDetails = (SecurityDemoUserDetails) auth.getPrincipal();
        String[] roles = securityDemoUserDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);
        return JWT.create()
                .withSubject(securityDemoUserDetails.getUsername())
                .withArrayClaim("roles", roles)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
    }

    public static DecodedJWT decodeToken(String token) throws JWTVerificationException {
        return JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()))
                .build()
                .verify(token);
    }
}
