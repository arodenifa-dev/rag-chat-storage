package com.ragchat.rag_chat_storage.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ragchat.rag_chat_storage.config.filter.ApiKeyAuthFilter;
import com.ragchat.rag_chat_storage.config.filter.ApiRateLimitFilter;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public ApiKeyAuthFilter apiKeyAuthFilter() {
        return new ApiKeyAuthFilter();
    }

    @Bean
    public ApiRateLimitFilter apiRateLimitFilter() {
        return new ApiRateLimitFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/swagger-ui.html"
            ).permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(apiKeyAuthFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(apiRateLimitFilter(), ApiKeyAuthFilter.class);

    return http.build();
    }

    
}
