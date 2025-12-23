package com.shopping_control.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping_control.dto.LoginRequest;
import com.shopping_control.dto.LoginResponse;
import com.shopping_control.entity.RefreshToken;
import com.shopping_control.entity.User;
import com.shopping_control.repository.UserRepository;
import com.shopping_control.security.UserDetailsImpl;
import com.shopping_control.service.JwtService;
import com.shopping_control.service.RefreshTokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {
        private final AuthenticationManager authManager;
        private final JwtService jwtService;
        private final RefreshTokenService refreshTokenService;
        private final UserRepository userRepository;

        public AuthController(AuthenticationManager authManager, JwtService jwtService,
                        RefreshTokenService refreshTokenService, UserRepository userRepository) {
                this.authManager = authManager;
                this.jwtService = jwtService;
                this.refreshTokenService = refreshTokenService;
                this.userRepository = userRepository;
        }

        @PostMapping("/login")
        public ResponseEntity<?> login(
                        @RequestBody LoginRequest request) {
                Authentication authentication = authManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));

                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

                User user = userRepository
                                .findByEmail(userDetails.getUsername())
                                .orElseThrow(() -> new RuntimeException("User not found"));

                String accessToken = jwtService.generateToken(user);

                RefreshToken refreshToken = refreshTokenService.create(user);

                return ResponseEntity.ok(
                                new LoginResponse(
                                                accessToken,
                                                refreshToken.getToken(),
                                                jwtService.getExpirationDate().toInstant().toEpochMilli()));
        }

}
