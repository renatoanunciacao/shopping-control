package com.shopping_control.service;

import org.springframework.stereotype.Service;

import com.shopping_control.client.GoogleOAuthClient;
import com.shopping_control.config.GoogleOAuthProperties;
import com.shopping_control.dto.AuthResponse;
import com.shopping_control.entity.RefreshToken;


@Service
public class GoogleAuthService {

    private final GoogleOAuthProperties googleOAuthProperties;
    private final GoogleOAuthClient googleOAuthClient;
    private final UserService userService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public GoogleAuthService(GoogleOAuthProperties googleOAuthProperties,
                             GoogleOAuthClient googleOAuthClient,
                             UserService userService,
                             JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.googleOAuthProperties = googleOAuthProperties;
        this.googleOAuthClient = googleOAuthClient;
        this.userService = userService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }


    public String buildAuthUrl() {
        return "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=" + googleOAuthProperties.getClientId() +
                "&redirect_uri=" + googleOAuthProperties.getRedirectUri() +
                "&response_type=code" +
                "&scope=openid email profile";
    }

    public AuthResponse handleCallback(String code) {
        // Trocar o código de autorização por um token de acesso
        var tokenResponse = googleOAuthClient.getToken(code);

        // Buscar as informações do usuário no Google
        var googleUserInfo = googleOAuthClient.getUserInfo(tokenResponse.getAccessToken());

        // Aqui você pode criar ou atualizar o usuário no seu sistema
        var user = userService.findOrCreateUser(googleUserInfo);

        // Gerar um token JWT para o usuário autenticado
        String jwtToken = jwtService.generateToken(user);

         RefreshToken refreshToken = refreshTokenService.create(user);

        return new AuthResponse(
                jwtToken,
                refreshToken.getToken());
    }


    
}
