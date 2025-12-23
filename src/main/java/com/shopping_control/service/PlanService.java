package com.shopping_control.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopping_control.entity.Plan;
import com.shopping_control.entity.User;
import com.shopping_control.repository.PlanRepository;
import com.shopping_control.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class PlanService {
    
    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    public PlanService(PlanRepository planRepository, UserRepository userRepository) {
        this.planRepository = planRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Plan createPlanIfNotExists(String name) {
        return planRepository.findByName(name).orElseGet(() -> planRepository.save(new Plan(name)));
    }

    @Transactional
    public void assignFreePlanToUsersWithoutPlan() {
        Plan freePlan = planRepository.findByName("FREE")
                .orElseThrow(() -> new IllegalStateException("FREE plan not found"));

        // Lógica para atribuir o plano FREE aos usuários sem plano
        // Isso pode envolver a injeção de um UserRepository e a atualização dos usuários
        List<User> usersWithoutPlan = userRepository.findAll()
        .stream()
        .filter(user -> user.getPlans().isEmpty()).toList();

        for (User user : usersWithoutPlan) {
            user.getPlans().add(freePlan);
            userRepository.save(user);
        }   
    }

    @Transactional
    public Plan getFreePlan() {
        return planRepository.findByName("FREE")
                .orElseThrow(() -> new RuntimeException("FREE plan not found"));
    }

    
}
