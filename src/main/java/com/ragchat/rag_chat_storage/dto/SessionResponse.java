package com.ragchat.rag_chat_storage.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.ragchat.rag_chat_storage.entity.MessageEntity;
import com.ragchat.rag_chat_storage.enums.Status;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Response model representing a chat session")
public class SessionResponse {

    @Schema(
        description = "Unique ID of the session",
        example = "550e8400-e29b-41d4-a716-446655440001"
    )
    private UUID sessionId;

    @Schema(description = "User ID info of the rag chat session", example = "user-code-genesis@gmail.com")
    private String userId;

    @Schema(description = "Name of the session", example = "My Support Chat")
    private String name;

    @Schema(description = "Mark or unmark session as favorite", example = "false")
    private Boolean favorite;

    @Schema(description = "Status of the session", example = "ACTIVE")
    private Status status;

    @Schema(
        description = "Timestamp when the session was created",
        example = "2026-01-30T12:33:35.395950439"
    )
    private LocalDateTime createdAt;

    @Schema(
        description = "Timestamp when the session was updated",
        example = "2026-01-30T12:33:35.395950439"
    )
    private LocalDateTime updatedAt;

    @Schema(
        description = "List of rag chat messages in this session"
    )
    private List<MessageEntity> messages;
    
}
