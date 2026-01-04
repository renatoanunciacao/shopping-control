package com.shopping_control.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.shopping_control.entity.enums.AuthProvider;
import com.shopping_control.entity.enums.Role;

public class UserTest {

    @Test
    void shouldCreateUserLocalCorrectly() {
        User user = new User("Renato", "renato@email.com", "password_encripted", AuthProvider.LOCAL);

        assertEquals("Renato", user.getName());
        assertEquals("renato@email.com", user.getEmail());
        assertEquals("password_encripted", user.getPassword());
        assertEquals(AuthProvider.LOCAL, user.getProvider());
        assertTrue(user.getActive());

    }

    @Test
    void shouldCreateUserWithDefaultRole() {
        User user = new User("Ana", "ana@email.com", "password_encripted", AuthProvider.LOCAL);
        user.setRole(Role.ROLE_USER);

        assertEquals("Ana", user.getName());
        assertEquals("ana@email.com", user.getEmail());
        assertEquals("password_encripted", user.getPassword());
        assertEquals(AuthProvider.LOCAL, user.getProvider());
        assertEquals(Role.ROLE_USER, user.getRole());
        assertTrue(user.getActive());

    }

}
