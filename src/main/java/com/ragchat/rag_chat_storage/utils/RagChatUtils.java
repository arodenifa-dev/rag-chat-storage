package com.ragchat.rag_chat_storage.utils;

public class RagChatUtils {

    // private constructor prevents instantiation
    private RagChatUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static final String HEADER_NAME = "x-api-key";


    public static final String API_KEY = "${api.key}";

    public static final long bucketCapacity = 2;
}
