package com.shopping_control.config;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shopping_control.service.JwtService;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

        private final JwtService jwtService;
        private final UserDetailsService userDetailsService;

        public JwtAuthenticationFilter(
                        JwtService jwtService,
                        UserDetailsService userDetailsService) {
                this.jwtService = jwtService;
                this.userDetailsService = userDetailsService;
        }

        /**
         * Define quais rotas NÃO devem passar pelo filtro JWT
         */
        @Override
        protected boolean shouldNotFilter(HttpServletRequest request) {
                String path = request.getServletPath();

                return path.startsWith("/auth/")
                                || path.equals("/users");
        }

        @Override
        protected void doFilterInternal(
                        HttpServletRequest request,
                        HttpServletResponse response,
                        FilterChain filterChain) throws ServletException, IOException {

                String authHeader = request.getHeader("Authorization");

                // Log para debug
                System.out.println("=== JWT Filter Debug ===");
                System.out.println("Path: " + request.getServletPath());
                System.out.println("Auth Header: " + authHeader);

                // Se não houver token, apenas segue o fluxo
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                        System.out.println("No valid token found - continuing without authentication");
                        filterChain.doFilter(request, response);
                        return;
                }

                try {
                        String token = authHeader.substring(7);
                        String username = jwtService.extractUsername(token);
                        List<String> roles = jwtService.extractRoles(token);

                        System.out.println("Token found - Username: " + username);
                        System.out.println("Roles: " + roles);

                        if (username != null
                                        && SecurityContextHolder.getContext().getAuthentication() == null) {

                                var userDetails = userDetailsService.loadUserByUsername(username);

                                if (jwtService.isTokenValid(token)) {

                                        Collection<GrantedAuthority> authorities = roles.stream()
                                                        .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(
                                                                        "ROLE_" + role))
                                                        .toList();

                                        var authentication = new UsernamePasswordAuthenticationToken(
                                                        userDetails,
                                                        null,
                                                        authorities);

                                        authentication.setDetails(
                                                        new org.springframework.security.web.authentication.WebAuthenticationDetailsSource()
                                                                        .buildDetails(request));

                                        SecurityContextHolder
                                                        .getContext()
                                                        .setAuthentication(authentication);
                                        
                                        System.out.println("Authentication successful for user: " + username);
                                } else {
                                        System.out.println("Token is invalid!");
                                }
                        }

                        filterChain.doFilter(request, response);
                } catch (JwtException e) {
                        System.out.println("JWT Exception: " + e.getMessage());
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        return;
                }

        }
}
