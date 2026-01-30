package com.ragchat.rag_chat_storage.service;

import java.util.UUID;

import com.ragchat.rag_chat_storage.dto.CreateSessionRequest;
import com.ragchat.rag_chat_storage.dto.SessionResponse;
import com.ragchat.rag_chat_storage.dto.UpdateSessionRequest;

public interface SessionService {

    public SessionResponse createSession(CreateSessionRequest session);
    public SessionResponse updateSession(UUID id, UpdateSessionRequest session);
    public void deleteSession(UUID sessionId);
}
