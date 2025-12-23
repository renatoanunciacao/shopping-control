package com.shopping_control.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shopping_control.dto.RefreshTokenRequest;
import com.shopping_control.dto.RefreshTokenResponse;
import com.shopping_control.entity.RefreshToken;
import com.shopping_control.service.JwtService;
import com.shopping_control.service.RefreshTokenService;

@RestController
@RequestMapping("/auth")
public class RefreshTokenController {
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    public RefreshTokenController(RefreshTokenService refreshTokenService, JwtService jwtService) {
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refresh(
            @RequestBody RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.validate(request.getRefreshToken());

        String accessToken = jwtService.generateToken(refreshToken.getUser());
        RefreshToken newRefreshToken = refreshTokenService.create(refreshToken.getUser());

        refreshTokenService.revoke(refreshToken);

        return ResponseEntity.ok(
                new RefreshTokenResponse(
                        accessToken,
                        newRefreshToken.getToken(),
                        jwtService.getExpirationDate().toInstant().toEpochMilli()));
    }
}