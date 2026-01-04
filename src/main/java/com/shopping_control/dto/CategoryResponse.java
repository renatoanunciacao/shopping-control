package com.shopping_control.dto;

import com.shopping_control.entity.Category;

public record CategoryResponse(
        Long id,
        String name) {
    public static CategoryResponse from(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName());
    }
}
