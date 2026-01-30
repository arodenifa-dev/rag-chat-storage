package com.ragchat.rag_chat_storage.mapper;


import com.ragchat.rag_chat_storage.dto.CreateSessionRequest;
import com.ragchat.rag_chat_storage.dto.SessionResponse;
import com.ragchat.rag_chat_storage.dto.UpdateSessionRequest;
import com.ragchat.rag_chat_storage.entity.SessionEntity;


public class SessionMapper {

    private SessionMapper() {
    throw new IllegalStateException("Utility class");
  }

    public static SessionEntity toSessionEntity(CreateSessionRequest sessionRequest) {

        SessionEntity session = new SessionEntity();
        session.setSessionId(null);
        session.setUserId(sessionRequest.getUserId());
        session.setName(sessionRequest.getName());
        session.setFavorite(sessionRequest.getFavorite() != null && sessionRequest.getFavorite());
        session.setStatus(sessionRequest.getStatus()); // default status

        return session;
    }

    /**
     * Applies non-null fields from SessionDto to an existing SessionEntity.
     * This supports PATCH-like partial updates.
     */
    public static SessionEntity toUpdateSessionEntity(UpdateSessionRequest sessionRequest, SessionEntity existingSessionEntity) {

        if (sessionRequest.getName() != null && !sessionRequest.getName().isBlank()) {
            existingSessionEntity.setName(sessionRequest.getName());
        }

        if (sessionRequest.getIsFavorite() != null) {
            existingSessionEntity.setFavorite(sessionRequest.getIsFavorite());
        }

        if (sessionRequest.getStatus() != null) {
            existingSessionEntity.setStatus(sessionRequest.getStatus());
        }

        return existingSessionEntity;
    }

    public static  SessionResponse toSessionResponse(SessionEntity sessionEntity) {
        if (sessionEntity == null) return null;

        SessionResponse session = new SessionResponse();
        session.setSessionId(sessionEntity.getSessionId());
        session.setUserId(sessionEntity.getUserId());
        session.setName(sessionEntity.getName());
        session.setFavorite(sessionEntity.getFavorite() != null && sessionEntity.getFavorite());
        session.setStatus(sessionEntity.getStatus()); // default status
        session.setCreatedAt(sessionEntity.getCreatedAt());
        session.setUpdatedAt(sessionEntity.getUpdatedAt());
        return session;
    }

    


    
}
