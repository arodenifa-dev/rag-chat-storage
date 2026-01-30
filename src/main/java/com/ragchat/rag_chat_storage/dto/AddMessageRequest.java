package com.ragchat.rag_chat_storage.dto;

import com.ragchat.rag_chat_storage.enums.SenderType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddMessageRequest {

    @NotNull
    private SenderType sender;
    @NotBlank
    private String content;
    private String ragContext;
    private String modelMetadata;

    
}
