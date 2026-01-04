package com.shopping_control.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopping_control.dto.CreateMarketRequest;
import com.shopping_control.dto.CreateMarketResponse;
import com.shopping_control.entity.Market;
import com.shopping_control.entity.User;
import com.shopping_control.repository.MarketRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MarketService {
    
    private final MarketRepository marketRepository;
    private final AuthService authService;

    public MarketService(MarketRepository marketRepository, AuthService authService) {
        this.marketRepository = marketRepository;
        this.authService = authService;
    }

    public Market create(CreateMarketRequest request){
        User authenticatedUser = authService.getAuthentidcatedUser();

        if(marketRepository.existsByCnpjAndUser(request.cnpj(), authenticatedUser)){
            throw new IllegalArgumentException("Market with this CNPJ already exists for the user");
        }

        Market market = new Market(request.name(), request.cnpj(), authenticatedUser);

        return marketRepository.save(market);
    }

    public Market findById(Long id){
        User authenticatedUser = authService.getAuthentidcatedUser();

        return marketRepository.findByIdAndUser(id, authenticatedUser)
                .orElseThrow(() -> new IllegalArgumentException("Market not found"));
    }

    public List<CreateMarketResponse> listAllMarkets () {
        List<Market> markets = marketRepository.findAll();

        return markets.stream()
                .map(market -> new CreateMarketResponse(
                        market.getId(),
                        market.getName(),
                        market.getCnpj(),
                        market.getUser().getId()))
                .toList();
    }

}
