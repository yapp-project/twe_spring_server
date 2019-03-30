package com.sajo.study.security;

import com.sajo.study.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JWTUtil implements Serializable {
    private static final long serialVersionUID = 1L;


    public enum Role {ROLE_USER}

    @Value("${springbootwebfluxjjwt.jjwt.secret}")
    private String secret;

    @Value("${springbootwebfluxjjwt.jjwt.expiration}")
    private String expirationTime;

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes())).parseClaimsJws(token).getBody();
    }

    String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    private Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        log.info(expiration.toString());
        return expiration.before(new Date());
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", Role.ROLE_USER);
        return doGenerateToken(claims, user.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {
        Long expirationTimeLong = Long.parseLong(expirationTime); //in second

        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512,secret.getBytes())
                .compact();
    }

    Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

}
