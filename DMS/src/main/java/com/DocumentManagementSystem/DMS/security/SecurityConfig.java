package com.DocumentManagementSystem.DMS.security;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .bearerTokenResolver(request -> {
                    String token = request.getHeader("Authorization");
                    if (token == null || !token.startsWith("Bearer ")) {
                        throw new RuntimeException("Missing or invalid token");
                    }
                    return token.substring(7); // Just return dummy token
                })
                .jwt(jwt -> jwt.decoder(token -> {
                    // Always accept the token as valid
                    return Jwt.withTokenValue(token).header("alg", "none").claim("sub", "user").build();
                }))
            );
        return http.build();
    }
}

