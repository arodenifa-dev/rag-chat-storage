package com.ragchat.rag_chat_storage.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ragchat.rag_chat_storage.enums.SenderType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Response model representing a message in a chat session")
public class MessageResponse {

    @Schema(
        description = "Unique ID of the message",
        example = "550e8400-e29b-41d4-a716-446655440000"
    )
    private UUID messageId;

    @Schema(description = "Sender of the message - USER/AI", example = "USER")
    private SenderType sender;

    @Schema(
        description = "Content of the message (user input or AI response)",
        example = "Hello, I need help with my order."
    )
    private String content;

    @Schema(description = "RAG context (RAG data), optional", example = "[{\"sourceId\":\"doc_201\",\"title\":\"Refund Policy\",\"excerpt\":\"Refunds are available within 30 days provided the product is unused.\",\"similarityScore\":0.93}]")
    private String ragContext;

    @Schema(description = "AI model metadata (AI only)", 
        example = "{\"model\":\"gpt-4.1\",\"temperature\":0.2}")
    private String modelMetadata;

     @Schema(
        description = "Timestamp when the message was created",
        example = "2026-01-30T12:33:35.395950439"
    )
    private LocalDateTime createdAt;
    
}
