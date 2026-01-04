package com.shopping_control.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopping_control.dto.CreateCategoryRequest;
import com.shopping_control.entity.Category;
import com.shopping_control.entity.Market;
import com.shopping_control.entity.User;
import com.shopping_control.exception.BusinessException;
import com.shopping_control.repository.CategoryRepository;
import com.shopping_control.repository.MarketRepository;
import com.shopping_control.repository.UserRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final MarketRepository marketRepository;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository, MarketRepository marketRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.marketRepository = marketRepository;
        this.userRepository = userRepository;
    }

    public Category create(CreateCategoryRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new BusinessException("User not found"));

        Market market = marketRepository.findByUserId(user.getId())
                .orElseThrow(() -> new BusinessException("Market not found for this user"));

        if (categoryRepository.existsByNameAndMarketId(request.name(), market.getId())) {
            throw new BusinessException("Category already exists in this market");
        }

        Category category = new Category(request.name(), market);
        return categoryRepository.save(category);
    }

    public List<Category> list(String userEmail){
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new BusinessException("User not found"));

        Market market = marketRepository.findByUserId(user.getId())
                .orElseThrow(() -> new BusinessException("Market not found for this user"));

                return categoryRepository.findAllByMarketId(market.getId());
    }

}
