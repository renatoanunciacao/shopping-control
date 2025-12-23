package com.shopping_control.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.shopping_control.entity.User;
import com.shopping_control.repository.UserRepository;
import com.shopping_control.security.UserDetailsImpl;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username)
            .orElseThrow();

        return new UserDetailsImpl(user);
    }
    
}
