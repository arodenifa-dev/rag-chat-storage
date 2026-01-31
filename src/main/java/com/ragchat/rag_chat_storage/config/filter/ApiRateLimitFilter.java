package com.ragchat.rag_chat_storage.config.filter;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ragchat.rag_chat_storage.utils.RagChatUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApiRateLimitFilter extends OncePerRequestFilter {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Value("${rate-limit.capacity}")
    private int capacity;

    @Value("${rate-limit.refill-per-minute}")
    private long refillPerMinute;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        return RagChatUtils.shouldNotFilter(request);

    }

    private Bucket createBucket() {
        Refill refill = Refill.intervally(refillPerMinute, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(capacity, refill);
        return Bucket.builder().addLimit(limit).build();
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String key = auth.getName();

        Bucket bucket = buckets.computeIfAbsent(key, k -> createBucket());

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(429); // Too Many Requests
            response.getWriter().write("Rate limit exceeded");
        }
    }
}