package com.ragchat.rag_chat_storage.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ragchat.rag_chat_storage.dto.AddMessageRequest;
import com.ragchat.rag_chat_storage.dto.MessageResponse;
import com.ragchat.rag_chat_storage.entity.MessageEntity;
import com.ragchat.rag_chat_storage.entity.SessionEntity;
import com.ragchat.rag_chat_storage.enums.SenderType;
import com.ragchat.rag_chat_storage.enums.Status;
import com.ragchat.rag_chat_storage.exception.InactiveSessionException;
import com.ragchat.rag_chat_storage.exception.SessionNotFoundException;
import com.ragchat.rag_chat_storage.mapper.MessageMapper;
import com.ragchat.rag_chat_storage.repository.MessageRepository;
import com.ragchat.rag_chat_storage.repository.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {

    @InjectMocks
    private MessageServiceImpl messageService;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private MessageRepository messageRepository;

    private UUID sessionId;
    private SessionEntity sessionEntity;
    private AddMessageRequest addMessageRequest;
    private MessageEntity messageEntity;
    private MessageResponse messageResponse;
    private UUID messageId;

    @BeforeEach
    void setUp() {
        sessionId = UUID.randomUUID();
        messageId = UUID.randomUUID();

        sessionEntity = new SessionEntity();
        sessionEntity.setSessionId(sessionId);
        sessionEntity.setUserId("user123");
        sessionEntity.setName("Test Session");
        sessionEntity.setStatus(Status.ACTIVE);

        addMessageRequest = new AddMessageRequest();
        addMessageRequest.setSender(SenderType.USER);
        addMessageRequest.setContent("Hello Test!");

        messageEntity = new MessageEntity();
        messageEntity.setSession(sessionEntity);
        messageEntity.setContent(addMessageRequest.getContent());
        messageEntity.setSender(null); // Will be mapped

        messageResponse = new MessageResponse();
        messageResponse.setContent(addMessageRequest.getContent());
        messageResponse.setMessageId(messageId);
    }

    @Test
    void testAddMessage_Success() {
        try (MockedStatic<MessageMapper> mapper = mockStatic(MessageMapper.class)) {
            mapper.when(() -> MessageMapper.toMessageEntity(addMessageRequest, sessionEntity))
                    .thenReturn(messageEntity);
            mapper.when(() -> MessageMapper.toMessageResponse(messageEntity))
                    .thenReturn(messageResponse);

            when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(sessionEntity));
            when(messageRepository.save(messageEntity)).thenReturn(messageEntity);

            MessageResponse response = messageService.addMessage(sessionId, addMessageRequest);

            assertNotNull(response);
            assertEquals(messageResponse.getContent(), response.getContent());
            verify(sessionRepository, times(1)).findById(sessionId);
            verify(messageRepository, times(1)).save(messageEntity);
        }
    }

    @Test
    void testAddMessage_SessionNotFound() {
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

        SessionNotFoundException ex = assertThrows(SessionNotFoundException.class,
                () -> messageService.addMessage(sessionId, addMessageRequest));

        assertEquals("Session not found with id: " + sessionId, ex.getMessage());
        verify(messageRepository, never()).save(any());
    }

    @Test
    void testAddMessage_InactiveSession() {
        sessionEntity.setStatus(Status.ARCHIVED);
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(sessionEntity));

        InactiveSessionException ex = assertThrows(InactiveSessionException.class,
                () -> messageService.addMessage(sessionId, addMessageRequest));

        assertEquals("Cannot add message to inactive session: " + sessionId, ex.getMessage());
        verify(messageRepository, never()).save(any());
    }

    @Test
    void testGetMessagesBySession() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").ascending());
        Page<MessageEntity> page = new PageImpl<>(List.of(messageEntity));

        try (MockedStatic<MessageMapper> mapper = mockStatic(MessageMapper.class)) {
            mapper.when(() -> MessageMapper.toMessageResponse(messageEntity)).thenReturn(messageResponse);

            when(messageRepository.findBySession_SessionIdOrderByCreatedAtAsc(sessionId, pageable))
                    .thenReturn(page);

            Page<MessageResponse> result = messageService.getMessagesBySession(sessionId, 0, 10);

            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
            assertEquals(messageResponse.getContent(), result.getContent().get(0).getContent());

            verify(messageRepository, times(1))
                    .findBySession_SessionIdOrderByCreatedAtAsc(sessionId, pageable);
        }
    }
}

