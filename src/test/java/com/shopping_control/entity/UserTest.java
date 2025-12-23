package com.shopping_control.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.shopping_control.entity.enums.AuthProvider;

public class UserTest {

    @Test
    void shouldCreateUserLocalCorrectly(){
        User user = new User("Renato", "renato@email.com", "password_encripted", AuthProvider.LOCAL);

        assertEquals("Renato", user.getName());
        assertEquals("renato@email.com", user.getEmail());
        assertEquals("password_encripted", user.getPassword());
        assertEquals(AuthProvider.LOCAL, user.getProvider());
        assertTrue(user.getActive());

    }
    
}
