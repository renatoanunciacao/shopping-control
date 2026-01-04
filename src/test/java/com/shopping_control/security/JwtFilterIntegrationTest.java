package com.shopping_control.security;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.shopping_control.dto.CreateUserRequest;
import com.shopping_control.entity.User;
import com.shopping_control.entity.enums.AuthProvider;
import com.shopping_control.entity.enums.Role;
import com.shopping_control.repository.UserRepository;
import com.shopping_control.service.JwtService;
import com.shopping_control.service.UserService;

import jakarta.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class JwtFilterIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    private Long testUserId;

    @BeforeEach
    void setup() {
        CreateUserRequest request = new CreateUserRequest("Integration Test User", "integration@test.com",
                "password");

        testUserId = userService.create(request).getId();
    }

    @Test
    void shouldAuthenticateWithValidToken() throws Exception {
        User user = userRepository.findByEmail("integration@test.com").orElseThrow();

        String token = jwtService.generateToken(user);

        mockMvc.perform(get("/test/secure-endpoint").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

    }

    @Test
    void shouldRejectRequestWithInvalidToken() throws Exception {
        String invalidToken = "Bearer invalid.token.here";

        mockMvc.perform(get("/test/secure-endpoint").header("Authorization", invalidToken))
                .andExpect(status().isUnauthorized());

    }

    @Test
    void shouldRejectRequestWithoutToken() throws Exception {
        mockMvc.perform(get("/test/secure-endpoint")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
    }

    void cleanup() {
        userRepository.deleteById(testUserId);
    }

}
