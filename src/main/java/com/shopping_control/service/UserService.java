package com.shopping_control.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopping_control.dto.CreateUserRequest;
import com.shopping_control.dto.GoogleUserInfo;
import com.shopping_control.entity.User;
import com.shopping_control.entity.enums.AuthProvider;
import com.shopping_control.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PlanService planService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, PlanService planService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.planService = planService;
    }

    public User create(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already in use");
        }

        String encryptedPassword = passwordEncoder.encode(request.password());

        User user = new User(
                request.name(),
                request.email(),
                encryptedPassword,
                AuthProvider.LOCAL);

        user.getPlans().add(planService.getFreePlan());

        return userRepository.save(user);
    }

    @Transactional
    public User findOrCreateUser(GoogleUserInfo googleUserInfo) {

        return userRepository.findByEmail(googleUserInfo.getEmail())
                .map(existingUser -> {
                    // Usuário já existe → garante provider
                    if (existingUser.getProvider() == null) {
                        existingUser.setProvider(AuthProvider.GOOGLE);
                    }
                    return existingUser;
                })
                .orElseGet(() -> {

                    User newUser = new User(
                            googleUserInfo.getName(),
                            googleUserInfo.getEmail(),
                            null,
                            AuthProvider.GOOGLE);

                    newUser.getPlans().add(planService.getFreePlan());

                    return userRepository.save(newUser);
                });
    }

}
