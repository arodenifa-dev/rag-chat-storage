package com.ragchat.rag_chat_storage.service;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.ragchat.rag_chat_storage.dto.AddMessageRequest;
import com.ragchat.rag_chat_storage.dto.MessageResponse;

public interface MessageService {

    public MessageResponse addMessage(UUID sessionId, AddMessageRequest messageRequest);
    Page<MessageResponse> getMessagesBySession(
            UUID sessionId,
            int page,
            int size
    );

    
}
