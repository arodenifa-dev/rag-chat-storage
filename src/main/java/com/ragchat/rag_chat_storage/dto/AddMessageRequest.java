package com.ragchat.rag_chat_storage.dto;

import com.ragchat.rag_chat_storage.enums.SenderType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Request to add a message in a RAG chat session")
public class AddMessageRequest {

    @NotNull
    @Schema(description = "Sender of the message - USER/AI", example = "USER")
    private SenderType sender;

    @NotBlank
    @Schema(description = "Content of the message (user input or AI response)", example = "Hello, I need help with my order.")
    private String content;

    @Schema(description = "RAG context (RAG data), optional", example = "[{\"sourceId\":\"doc_201\",\"title\":\"Refund Policy\",\"excerpt\":\"Refunds are available within 30 days provided the product is unused.\",\"similarityScore\":0.93}]")
    private String ragContext;

    @Schema(description = "AI model metadata (AI only)", 
        example = "{\"model\":\"gpt-4.1\",\"temperature\":0.2}")
    private String modelMetadata;

    
}
