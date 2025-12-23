package com.shopping_control.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.shopping_control.entity.Plan;
import com.shopping_control.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

    private static final String SECRET_KEY = "minha-chave-super-secreta-com-mais-de-32-bytes";
    private static final long EXPIRATION_TIME = 5000;
    private final Key secretKey;

    public JwtService() {
        this.secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public Date getExpirationDate() {
        return new Date(System.currentTimeMillis() + EXPIRATION_TIME);
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(User user) {
       Date now = new Date();
    Date expiration = getExpirationDate();

    return Jwts.builder()
            .setSubject(user.getEmail())
            .claim("userId", user.getId())
            .claim("provider", user.getProvider().name())
            .claim("plans", user.getPlans()
                    .stream()
                    .map(Plan::getName)
                    .toList())
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(secretKey)
            .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {

        try {
            Claims claims = extractClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Instant getExpirationFromToken(String token) {
        return extractClaims(token).getExpiration().toInstant();
    }
}
