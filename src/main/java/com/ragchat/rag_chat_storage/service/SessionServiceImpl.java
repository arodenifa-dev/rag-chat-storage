package com.ragchat.rag_chat_storage.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ragchat.rag_chat_storage.dto.CreateSessionRequest;
import com.ragchat.rag_chat_storage.dto.SessionResponse;
import com.ragchat.rag_chat_storage.dto.UpdateSessionRequest;
import com.ragchat.rag_chat_storage.entity.SessionEntity;
import com.ragchat.rag_chat_storage.exception.SessionNotFoundException;
import com.ragchat.rag_chat_storage.mapper.SessionMapper;
import com.ragchat.rag_chat_storage.repository.SessionRepository;
import com.ragchat.rag_chat_storage.utils.AppLogger;

import jakarta.transaction.Transactional;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    SessionRepository sessionRepository;

    private static final Logger log = AppLogger.getLogger(SessionServiceImpl.class);

    @Override
    @Transactional
    public SessionResponse createSession(CreateSessionRequest session) {

        AppLogger.info(log, "Save session to database for user: {}", session.getUserId());
        SessionEntity savedSession = sessionRepository.save(SessionMapper.toSessionEntity(session));
        return SessionMapper.toSessionResponse(savedSession);

    }

    @Override
    @Transactional
    public SessionResponse updateSession(UUID sessionId, UpdateSessionRequest sessionRequest) {

        // 1. Fetch existing session
        SessionEntity existingSessionEntity = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new SessionNotFoundException("Session not found with id: " + sessionId));

        // 2. Update allowed fields ONLY if present
        existingSessionEntity = SessionMapper.toUpdateSessionEntity(sessionRequest, existingSessionEntity);

        // 3. Save updated session
        AppLogger.info(log, "Update session in database for session id: {}", sessionId);
        SessionEntity updatedSessionEntity = sessionRepository.save(existingSessionEntity);

        // 4. Map entity → response DTO
        return SessionMapper.toSessionResponse(updatedSessionEntity);

    }

    @Override
    @Transactional
    public void deleteSession(UUID sessionId) {

        SessionEntity session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new SessionNotFoundException("Session not found with id: " + sessionId));
        sessionRepository.delete(session); // cascade deletes messages

    }

}
