package com.shopping_control.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping_control.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByNameAndMarketId(String name, Long marketId);

    List<Category> findAllByMarketId(Long marketId);
}
