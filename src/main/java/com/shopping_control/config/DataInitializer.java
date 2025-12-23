package com.shopping_control.config;

import org.springframework.context.annotation.Configuration;

import com.shopping_control.service.PlanService;

@Configuration
public class DataInitializer {
    
    private final PlanService planService;

    public DataInitializer(PlanService planService) {
        this.planService = planService;
    }

    public void initializeData() {
        planService.createPlanIfNotExists("FREE");
        planService.createPlanIfNotExists("PREMIUM");
    }

}
