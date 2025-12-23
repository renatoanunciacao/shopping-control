package com.shopping_control.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String accessToken;

    private String refreshToken;


}
