package com.shopping_control.dto;

public class LoginResponse {
    private final String token;
    private final String refreshToken;
    private final Long expiresAt; 

    public LoginResponse(String token, String refreshToken, Long expiresAt) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Long getExpiresAt() {
        return expiresAt;
    }
}
