package com.shopping_control.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shopping_control.entity.User;
import com.shopping_control.entity.enums.AuthProvider;
import com.shopping_control.entity.enums.Role;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {
    
    @InjectMocks
    private JwtService jwtService;

    @Test
    void shouldGenerateTokenWithRoles(){
        User user = new User("Test User", "testuser@email.com", "password_encripted", AuthProvider.LOCAL);

        user.setRole(Role.ROLE_USER);

        String token = jwtService.generateToken(user);

        assertNotNull(token);

        assertEquals("testuser@email.com", jwtService.extractUsername(token));

        List<String> roles = jwtService.extractRoles(token);
        assertTrue(roles.contains("ROLE_USER"));
    }
}
