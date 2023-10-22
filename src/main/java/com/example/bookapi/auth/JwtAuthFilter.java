package com.example.bookapi.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var bearerToken = jwtService.getBearerToken(request);
        if (isEmpty(bearerToken) || !jwtService.validateToken(bearerToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        var username = jwtService.extractUsername(bearerToken);
        var user = userDetailsService.loadUserByUsername(username);
        if (isEmpty(user)) {
            filterChain.doFilter(request, response);
            return;
        }

        var isTokenValid = tokenRepository.findByToken(bearerToken).map(t -> !t.isExpired() && !t.isRevoked()).orElse(false);

        if (jwtService.isTokenValid(bearerToken, user) && isTokenValid) {
            var authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
