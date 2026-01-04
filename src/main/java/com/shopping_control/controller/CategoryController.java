package com.shopping_control.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping_control.dto.CategoryResponse;
import com.shopping_control.dto.CreateCategoryRequest;
import com.shopping_control.entity.Category;
import com.shopping_control.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CreateCategoryRequest request, Authentication authentication) {

       var category = categoryService.create(request, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> list(Authentication authentication){
        List<CategoryResponse> response = categoryService.list(authentication.getName()).stream().map(CategoryResponse::from).toList();
        return ResponseEntity.ok(response);
    }
}
