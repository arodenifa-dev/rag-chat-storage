package com.ragchat.rag_chat_storage.dto;

import com.ragchat.rag_chat_storage.enums.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSessionRequest {


    private String name;
    private Boolean isFavorite;
    private Status status;
    
}
