package com.ragchat.rag_chat_storage.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ragchat.rag_chat_storage.utils.RagChatUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ApiKeyAuthFilter extends OncePerRequestFilter {

    @Value(RagChatUtils.API_KEY)
    private String apiKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String requestKey = request.getHeader(RagChatUtils.HEADER_NAME);

        if (apiKey == null || !apiKey.equals(requestKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Invalid API key!!!");
            return;
        }

        // ✅ Set authentication in SecurityContext
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                "apiKeyUser", // principal
                null, // credentials
                Collections.emptyList() // authorities
        );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
