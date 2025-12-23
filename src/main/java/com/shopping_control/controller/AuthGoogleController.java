package com.shopping_control.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopping_control.dto.AuthResponse;
import com.shopping_control.service.GoogleAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth/google")
@RequiredArgsConstructor
public class AuthGoogleController {

    private final GoogleAuthService googleAuthService;



    @GetMapping("/login")
    public ResponseEntity<Void> login() {
        String googleAuthUrl = googleAuthService.buildAuthUrl();

        return ResponseEntity.status(302)
                .header("Location", googleAuthUrl)
                .build();
    }

    @GetMapping("/callback")
    public ResponseEntity<AuthResponse> callback(@RequestParam("code") String code) {
        AuthResponse response = googleAuthService.handleCallback(code);

        return ResponseEntity.ok(response);
    }

}
