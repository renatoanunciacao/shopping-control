package com.shopping_control.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping_control.dto.CreateMarketRequest;
import com.shopping_control.dto.CreateMarketResponse;
import com.shopping_control.entity.Market;
import com.shopping_control.service.MarketService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/markets")
public class MarketController {
    private final MarketService marketService;

    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }

    @PostMapping
    public ResponseEntity<CreateMarketResponse> createMarket(
            @Valid @RequestBody CreateMarketRequest request) {
        Market market = marketService.create(request);

        CreateMarketResponse response = new CreateMarketResponse(market.getId(), market.getName(), market.getCnpj(),
                market.getUser().getId());

        return response != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping
    public ResponseEntity<List<CreateMarketResponse>> getAllMarket() {
        List<CreateMarketResponse> markets = marketService.listAllMarkets();

        return ResponseEntity.ok(markets);
    }
}
