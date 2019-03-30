package com.sajo.study.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class AuthResponse {
    private String token;
}
