package com.example.bookapi.auth.dto;

import com.example.bookapi.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthToken {
    private String accessToken;
    private String refreshToken;
    private User user;
}