package com.chatapp.realtimechatapp.jwt;

import com.chatapp.realtimechatapp.model.User;
import com.chatapp.realtimechatapp.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String jwtToken = null;
        Long userId = null;

        // ✅ 1. Get JWT from Authorization header
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
        }

        // ✅ 2. Or from cookies (if you really use cookie auth)
        if (jwtToken == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("JWT".equals(cookie.getName())) {
                        jwtToken = cookie.getValue();
                        break;
                    }
                }
            }
        }

        // ✅ 3. If no token, skip authentication
        if (jwtToken == null || jwtToken.trim().isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        // ✅ 4. Extract user ID *safely*
        try {
            userId = jwtService.extractUserId(jwtToken);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        // ✅ 5. Authenticate user if not already authenticated
        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            User userDetails = userRepository.findById(userId)
                    .orElse(null);

            if (userDetails != null && jwtService.isTokenValid(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, Collections.emptyList());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

}
