package com.shopping_control.service;

import java.time.Duration;
import java.time.Instant;

import org.springframework.stereotype.Service;

import com.shopping_control.entity.RefreshToken;
import com.shopping_control.entity.User;
import com.shopping_control.repository.RefreshTokenRepository;
import com.shopping_control.utils.TokenGenerator;

import jakarta.transaction.Transactional;

@Service
 @Transactional
public class RefreshTokenService {

    private static final Duration REFRESH_TOKEN_DURATION = Duration.ofHours(4);

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken create(User user) {

        refreshTokenRepository.findAllByUserAndRevokedFalse(user).forEach(RefreshToken::revoke);

        String token = TokenGenerator.generateSecureToken();
        Instant expiresAt = Instant.now().plus(REFRESH_TOKEN_DURATION);

        RefreshToken refreshToken = new RefreshToken(token, user, expiresAt);

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken validate(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshToken.isRevoked() || refreshToken.isExpired()) {
            throw new RuntimeException("Refresh token is revoked or expired");
        }

        return refreshToken;
    }

    public void revoke(RefreshToken refreshToken) {
        refreshToken.revoke();
        refreshTokenRepository.save(refreshToken);
    }
    
}
