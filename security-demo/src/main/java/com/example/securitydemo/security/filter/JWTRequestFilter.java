package com.example.securitydemo.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.securitydemo.security.SecurityConstants;
import com.example.securitydemo.security.exception.JwtVerificationException;
import com.example.securitydemo.security.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JWTRequestFilter extends AbstractAuthenticationProcessingFilter {

    public JWTRequestFilter(String uri, AuthenticationManager authenticationManager, AuthenticationFailureHandler failureHandler) {
        super(new AntPathRequestMatcher(uri));
        this.setAuthenticationFailureHandler(failureHandler);
        this.setAuthenticationManager(authenticationManager);
    }

    private Authentication getAuthentication(String header) throws JWTVerificationException {
        DecodedJWT decodedJWT = JwtUtil.decodeToken(header.replace(SecurityConstants.TOKEN_PREFIX, ""));
        List<GrantedAuthority> authorities = Arrays.stream(decodedJWT.getClaim("roles").asArray(String.class))
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        Authentication jwtAuthenticationToken = new JwtAuthenticationToken(decodedJWT.getSubject(), authorities);
        jwtAuthenticationToken.setAuthenticated(true);
        return jwtAuthenticationToken;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String header = request.getHeader(SecurityConstants.HEADER_STRING);
        try {
            if (!StringUtils.isEmpty(header) && header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
                return getAuthentication(header);
            }
            throw new AuthenticationCredentialsNotFoundException("Authentication token not found. Authenticate to get a token!!");
        } catch (TokenExpiredException e) {
            throw new CredentialsExpiredException(e.getMessage());
        } catch (JWTVerificationException e) {
            throw new JwtVerificationException("Invalid Authentication token!!");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }
}
