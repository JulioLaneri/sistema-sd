package com.sd.sistemasd.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private String email;
    private String accessToken;
    private Set<String> roles;

    public AuthResponse() {
        throw new IllegalArgumentException("User email and access token are required");
    }


}
