package com.shopping_control.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.shopping_control.repository.RefreshTokenRepository;

import jakarta.transaction.Transactional;

@Component
public class RefreshTokenCleanupTask {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenCleanupTask(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    @Scheduled(fixedRate = 600000)
    public void cleanupExpiredOrRevokedTokens() {
        int deletedCount = refreshTokenRepository.deleteExpiredOrRevoked(java.time.Instant.now());
        System.out.println("Cleanup Task: Deleted " + deletedCount + " expired or revoked refresh tokens.");
    }
    
}
