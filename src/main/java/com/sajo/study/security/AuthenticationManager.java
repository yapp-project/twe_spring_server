package com.sajo.study.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Slf4j
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private JWTUtil jwtUtil;

    @Autowired
    public AuthenticationManager(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String userName;
        try {
            userName = jwtUtil.getUsernameFromToken(authToken);
        } catch (Exception e) {
            userName = null;
        }
        if (userName != null && jwtUtil.validateToken(authToken)) {
            ArrayList<GrantedAuthority> authority = new ArrayList<>();
            authority.add(new SimpleGrantedAuthority(JWTUtil.Role.ROLE_USER.name()));
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userName,
                    null,
                    authority
            );
            return Mono.just(auth);
        } else {
            return Mono.empty();
        }
    }
}
