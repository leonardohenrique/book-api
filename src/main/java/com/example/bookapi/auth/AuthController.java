package com.example.bookapi.auth;

import com.example.bookapi.auth.dto.LoginDTO;
import com.example.bookapi.auth.dto.TokenDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public TokenDTO login(@RequestBody LoginDTO loginDTO) {
        return service.login(loginDTO);
    }

    public void logout() {

    }

    @GetMapping("/user")
    public Authentication user() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @PostMapping("/refresh")
    public void refresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refresh(request, response);
    }

}
