package com.shopping_control.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping_control.entity.Market;
import com.shopping_control.entity.User;

public interface MarketRepository extends JpaRepository<Market, Long> {
    
    boolean existsByCnpjAndUser(String cnpj, User user);

    Optional<Market> findByIdAndUser(Long id, User user);

    Optional<Market> findByUser(User user);

    Optional<Market> findByUserId(Long userId);
}
