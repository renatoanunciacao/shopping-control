package com.shopping_control.dto;


public class RefreshTokenResponse {
    
    private final String accessToken;
    private final String refreshToken;
    private final Long expiresAt;

    public RefreshTokenResponse(String accessToken, String refreshToken, Long expiresAt) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Long getExpiresAt() {
        return expiresAt;
    }
}
