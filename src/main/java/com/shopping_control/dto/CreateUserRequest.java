package com.shopping_control.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(

    @NotBlank String name,
    @NotBlank @Email String email,
    @NotBlank String password

) {
    
}
