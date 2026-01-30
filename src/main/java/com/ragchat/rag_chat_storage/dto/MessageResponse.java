package com.ragchat.rag_chat_storage.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ragchat.rag_chat_storage.enums.SenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageResponse {

    private UUID messageId;
    private SenderType sender;
    private String content;
    private String ragContext;
    private String modelMetadata;
    private LocalDateTime createdAt;
    
}
