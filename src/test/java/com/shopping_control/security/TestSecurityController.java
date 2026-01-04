package com.shopping_control.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestSecurityController {
    
    @GetMapping("/secure-endpoint")
    public ResponseEntity<String> secureEndpoint() {
        return ResponseEntity.ok("This is a secure endpoint");
    }
}
