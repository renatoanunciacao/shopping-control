package com.shopping_control.service;

import com.shopping_control.repository.RefreshTokenRepository;

import jakarta.transaction.Transactional;

public class RefreshTokenCleanupService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenCleanupService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public int cleanupExpiredOrRevokedTokens() {
        return refreshTokenRepository.deleteExpiredOrRevoked(java.time.Instant.now());
    }
    
}
