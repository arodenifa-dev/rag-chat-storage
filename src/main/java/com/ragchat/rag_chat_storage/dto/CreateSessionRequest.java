package com.ragchat.rag_chat_storage.dto;

import com.ragchat.rag_chat_storage.enums.Status;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Request to create a new RAG chat session")
public class CreateSessionRequest {

    @NotBlank
    @Schema(description = "User ID info of the rag chat session", example = "2f2c55ab-225d-4292-8799-66e17322c262")
    private String userId;

    @Schema(description = "Name of the session", example = "My Support Chat")
    private String name;

    @Schema(description = "Mark or unmark session as favorite", example = "false")
    private Boolean favorite;

    @Schema(description = "Status of the session", example = "ACTIVE")
    private Status status;

}
