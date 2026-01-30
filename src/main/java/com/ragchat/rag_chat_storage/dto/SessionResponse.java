package com.ragchat.rag_chat_storage.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.ragchat.rag_chat_storage.entity.MessageEntity;
import com.ragchat.rag_chat_storage.enums.Status;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SessionResponse {

    private UUID sessionId;
    private String userId;
    private String name;
    private Boolean favorite;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<MessageEntity> messages;
    
}
