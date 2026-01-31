package com.ragchat.rag_chat_storage.dto;

import com.ragchat.rag_chat_storage.enums.Status;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request to update a chat session")
public class UpdateSessionRequest {

    @Schema(description = "Name of the session", example = "My Support Chat")
    private String name;

    @Schema(description = "Mark or unmark session as favorite", example = "false")
    private Boolean isFavorite;

    @Schema(description = "Status of the session", example = "ACTIVE")
    private Status status;
    
}
