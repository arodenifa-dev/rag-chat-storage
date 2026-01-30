package com.ragchat.rag_chat_storage.dto;

import java.util.List;

import com.ragchat.rag_chat_storage.entity.MessageEntity;
import com.ragchat.rag_chat_storage.enums.Status;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateSessionRequest {

    @NotBlank
    private String userId;
    private String name;
    private Boolean favorite;
    private Status status;
    private List<MessageEntity> messages;

}
