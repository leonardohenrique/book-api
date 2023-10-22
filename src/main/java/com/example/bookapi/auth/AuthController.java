package com.example.bookapi.auth;

import com.example.bookapi.auth.dto.AuthToken;
import com.example.bookapi.auth.dto.Credentials;
import com.example.bookapi.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public AuthToken login(@RequestBody Credentials credentials) {
        return service.login(credentials);
    }

    public void logout() {

    }

    @GetMapping("/user")
    public Optional<User> getUser() {
        return service.getUser();
    }

    @PostMapping("/refresh")
    public void refresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refresh(request, response);
    }

}
