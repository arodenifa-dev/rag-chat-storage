package com.ragchat.rag_chat_storage.exception;


public class SessionNotFoundException extends RuntimeException {

    public SessionNotFoundException(String message) {
        super(message);
    }
}