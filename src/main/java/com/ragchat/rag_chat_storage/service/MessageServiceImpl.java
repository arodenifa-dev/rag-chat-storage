package com.ragchat.rag_chat_storage.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ragchat.rag_chat_storage.dto.AddMessageRequest;
import com.ragchat.rag_chat_storage.dto.MessageResponse;
import com.ragchat.rag_chat_storage.entity.MessageEntity;
import com.ragchat.rag_chat_storage.entity.SessionEntity;
import com.ragchat.rag_chat_storage.enums.Status;
import com.ragchat.rag_chat_storage.exception.InactiveSessionException;
import com.ragchat.rag_chat_storage.exception.SessionNotFoundException;
import com.ragchat.rag_chat_storage.mapper.MessageMapper;
import com.ragchat.rag_chat_storage.repository.MessageRepository;
import com.ragchat.rag_chat_storage.repository.SessionRepository;
import com.ragchat.rag_chat_storage.utils.AppLogger;

import jakarta.transaction.Transactional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    MessageRepository messageRepository;

    private static final Logger log = AppLogger.getLogger(MessageServiceImpl.class);

    @Override
    @Transactional
    public MessageResponse addMessage(UUID sessionId, AddMessageRequest messageRequest) {

        // 1. Fetch the session
        SessionEntity existingSessionEntity = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new SessionNotFoundException("Session not found with id: " + sessionId));

        // 2. Validate session is active
        if (existingSessionEntity.getStatus() != Status.ACTIVE) {
            throw new InactiveSessionException(
                    "Cannot add message to inactive session: " + sessionId);
        }

        // 3. Map add message request to entity
        MessageEntity messageEntity = MessageMapper.toMessageEntity(messageRequest, existingSessionEntity);

        // 4. Save message

        AppLogger.info(log, "Add message in database for session id : {}", sessionId);
        MessageEntity savMessageEntity = messageRepository.save(messageEntity);

        return MessageMapper.toMessageResponse(savMessageEntity);

    }

    @Override
    public Page<MessageResponse> getMessagesBySession(
            UUID sessionId,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").ascending());

        AppLogger.info(log, "Fetch messages from database for session id : {}", sessionId);
        Page<MessageEntity> messages = messageRepository.findBySession_SessionIdOrderByCreatedAtAsc(
                sessionId,
                pageable);

        return messages.map(MessageMapper::toMessageResponse);

    }

}
