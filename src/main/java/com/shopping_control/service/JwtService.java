package com.shopping_control.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.shopping_control.entity.Plan;
import com.shopping_control.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

    private static final String SECRET_KEY = "minha-chave-super-secreta-com-mais-de-32-bytes";
    private static final long EXPIRATION_TIME = 1800000; // 30 minutos em milissegundos
    private final Key secretKey;

    public JwtService() {
        this.secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public Date getExpirationDate() {
        // Usa UTC para evitar problemas de timezone
        Instant now = Instant.now();
        Instant expiration = now.plusMillis(EXPIRATION_TIME);
        return Date.from(expiration);
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(User user) {
        Instant now = Instant.now();
        Date issuedAt = Date.from(now);
        Date expiration = getExpirationDate();

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId())
                .claim("provider", user.getProvider().name())
                .claim("roles", List.of(user.getRole().name()))
                .claim("plans", user.getPlans()
                        .stream()
                        .map(Plan::getName)
                        .toList())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractClaims(token);

        Object rolesObject = claims.get("roles");

        if (rolesObject == null) {
            return List.of();
        }

        return ((List<?>) rolesObject).stream()
                .map(Object::toString)
                .toList();
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
