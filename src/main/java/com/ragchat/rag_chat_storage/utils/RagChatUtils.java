package com.ragchat.rag_chat_storage.utils;

import jakarta.servlet.http.HttpServletRequest;

public class RagChatUtils {

    // private constructor prevents instantiation
    private RagChatUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static final String HEADER_NAME = "x-api-key";


    public static final String API_KEY = "${api.key}";

    public static final long bucketCapacity = 2;

    public static boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getRequestURI();

        return path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.equals("/swagger-ui.html")
                || path.startsWith("/actuator/health");

    }

}
