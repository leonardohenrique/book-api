package com.example.bookapi.auth;

import com.example.bookapi.auth.dto.AuthToken;
import com.example.bookapi.auth.dto.Credentials;
import com.example.bookapi.user.entity.User;
import com.example.bookapi.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public AuthToken login(Credentials credentials) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var user = userRepository.findByUsername(credentials.getUsername()).orElseThrow();

        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        return new AuthToken(accessToken, refreshToken, user);
    }

    private void saveUserToken(User user, String accessToken) {
        var token = Token.builder().user(user).token(accessToken).tokenType(TokenType.BEARER).expired(false).revoked(false).build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var refreshToken = jwtService.getBearerToken(request);
        if (ObjectUtils.isEmpty(refreshToken)) {
            return;
        }

        var username = jwtService.extractUsername(refreshToken);
        if (ObjectUtils.isEmpty(username)) {
            return;
        }

        var user = this.userRepository.findByUsername(username).orElseThrow();

        if (jwtService.isTokenValid(refreshToken, user)) {
            var accessToken = jwtService.generateToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, accessToken);
            var authResponse = new AuthToken(accessToken, refreshToken, user);
            new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
        }
    }

    public Optional<User> getUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = (String) authentication.getPrincipal();
        return userRepository.findByUsername(username);
    }
}
