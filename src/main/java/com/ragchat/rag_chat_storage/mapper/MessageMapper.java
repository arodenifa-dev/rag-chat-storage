package com.ragchat.rag_chat_storage.mapper;


import com.ragchat.rag_chat_storage.dto.AddMessageRequest;
import com.ragchat.rag_chat_storage.dto.MessageResponse;
import com.ragchat.rag_chat_storage.entity.MessageEntity;
import com.ragchat.rag_chat_storage.entity.SessionEntity;


public class MessageMapper {

    private MessageMapper() {
    throw new IllegalStateException("Utility class");
  }

    public static MessageEntity toMessageEntity(AddMessageRequest messageRequest, SessionEntity existingSessionEntity) {

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setSession(existingSessionEntity); // IMPORTANT
        messageEntity.setSender(messageRequest.getSender());
        messageEntity.setContent(messageRequest.getContent());
        messageEntity.setRagContext(messageRequest.getRagContext());
        messageEntity.setModelMetadata(messageRequest.getModelMetadata());

        return messageEntity;
    }

    /**
     * Converts MessageEntity to MessageResponse
     */
    public static MessageResponse toMessageResponse(MessageEntity entity) {
        if (entity == null) {
            return null;
        } 

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessageId(entity.getMessageId());
        messageResponse.setSender(entity.getSender());
        messageResponse.setContent(entity.getContent());
        messageResponse.setRagContext(entity.getRagContext());
        messageResponse.setModelMetadata(entity.getModelMetadata());
        messageResponse.setCreatedAt(entity.getCreatedAt());

        return messageResponse;
    }



}
