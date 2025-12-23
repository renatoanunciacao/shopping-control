package com.shopping_control.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shopping_control.entity.RefreshToken;
import com.shopping_control.entity.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findAllByUserAndRevokedFalse(User user);

     @Modifying
    @Query("""
        DELETE FROM RefreshToken rt
        WHERE rt.expiresAt < :now
           OR rt.revoked = true
    """)
    int deleteExpiredOrRevoked(@Param("now") Instant now);
    
}
