package com.shopping_control.client;

import com.shopping_control.config.GoogleOAuthProperties;
import com.shopping_control.dto.GoogleTokenResponse;
import com.shopping_control.dto.GoogleUserInfo;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class GoogleOAuthClient {

    private final GoogleOAuthProperties properties;
    private static final String TOKEN_URL =
            "https://oauth2.googleapis.com/token";

    private static final String USER_INFO_URL =
            "https://www.googleapis.com/oauth2/v2/userinfo";

    private final RestTemplate restTemplate;

    public GoogleOAuthClient(RestTemplate restTemplate, GoogleOAuthProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    public GoogleTokenResponse getToken(String code){

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("code", code);
        requestBody.add("client_id", properties.getClientId());
        requestBody.add("client_secret", properties.getClientSecret());
        requestBody.add("redirect_uri", properties.getRedirectUri());
        requestBody.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


        HttpEntity<?> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<GoogleTokenResponse> response =
                restTemplate.postForEntity(
                        TOKEN_URL,
                        request,
                        GoogleTokenResponse.class
                );

        return response.getBody();
    }

     public GoogleUserInfo getUserInfo(String accessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<?> request = new HttpEntity<>(headers);

        ResponseEntity<GoogleUserInfo> response =
                restTemplate.exchange(
                        USER_INFO_URL,
                        HttpMethod.GET,
                        request,
                        GoogleUserInfo.class
                );

        return response.getBody();
    }
}
