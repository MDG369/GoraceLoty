package com.goraceloty.apigateway.auth.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse {
    private String token;
    private Integer userId;
    public AuthenticationResponse(String token, Integer userId) {
        this.token = token;
        this.userId = userId;
    }
}
