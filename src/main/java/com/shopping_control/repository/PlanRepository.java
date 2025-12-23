package com.shopping_control.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shopping_control.entity.Plan;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    Optional<Plan> findByName(String name);
}
