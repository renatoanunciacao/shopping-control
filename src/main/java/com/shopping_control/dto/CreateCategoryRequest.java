package com.shopping_control.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(
    @NotBlank String name
) {
    
}
