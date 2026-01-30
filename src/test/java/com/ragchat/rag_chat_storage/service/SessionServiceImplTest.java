package com.ragchat.rag_chat_storage.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import com.ragchat.rag_chat_storage.dto.CreateSessionRequest;
import com.ragchat.rag_chat_storage.dto.SessionResponse;
import com.ragchat.rag_chat_storage.dto.UpdateSessionRequest;
import com.ragchat.rag_chat_storage.entity.SessionEntity;
import com.ragchat.rag_chat_storage.exception.SessionNotFoundException;
import com.ragchat.rag_chat_storage.mapper.SessionMapper;
import com.ragchat.rag_chat_storage.repository.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class SessionServiceImplTest {

    @InjectMocks
    private SessionServiceImpl sessionService;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private SessionMapper sessionMapper; // Optional if mapper is static, we can mock static using Mockito 4.0+

    private CreateSessionRequest createRequest;
    private UpdateSessionRequest updateRequest;
    private SessionEntity sessionEntity;
    private SessionResponse sessionResponse;
    private UUID sessionId;

    @BeforeEach
    void setUp() {
        sessionId = UUID.randomUUID();

        createRequest = new CreateSessionRequest();
        createRequest.setUserId("user123");
        createRequest.setName("Test Session");
        createRequest.setFavorite(false);

        updateRequest = new UpdateSessionRequest();
        updateRequest.setName("Updated Session");
        updateRequest.setIsFavorite(true);

        sessionEntity = new SessionEntity();
        sessionEntity.setSessionId(sessionId);
        sessionEntity.setUserId("user123");
        sessionEntity.setName("Test Session");
        sessionEntity.setFavorite(false);

        sessionResponse = new SessionResponse();
        sessionResponse.setSessionId(sessionId);
        sessionResponse.setUserId("user123");
        sessionResponse.setName("Test Session");
        sessionResponse.setFavorite(false);
    }

    @Test
    void testCreateSession() {
        // If SessionMapper methods are static, you can use Mockito.mockStatic
        try (MockedStatic<SessionMapper> mapper = mockStatic(SessionMapper.class)) {
            mapper.when(() -> SessionMapper.toSessionEntity(createRequest)).thenReturn(sessionEntity);
            mapper.when(() -> SessionMapper.toSessionResponse(sessionEntity)).thenReturn(sessionResponse);

            when(sessionRepository.save(sessionEntity)).thenReturn(sessionEntity);

            SessionResponse response = sessionService.createSession(createRequest);

            assertNotNull(response);
            assertEquals(sessionResponse.getSessionId(), response.getSessionId());
            verify(sessionRepository, times(1)).save(sessionEntity);
        }
    }

    @Test
    void testUpdateSession_Success() {
        try (MockedStatic<SessionMapper> mapper = mockStatic(SessionMapper.class)) {

            mapper.when(() -> SessionMapper.toUpdateSessionEntity(updateRequest, sessionEntity))
                    .thenReturn(sessionEntity);
            mapper.when(() -> SessionMapper.toSessionResponse(sessionEntity))
                    .thenReturn(sessionResponse);

            when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(sessionEntity));
            when(sessionRepository.save(sessionEntity)).thenReturn(sessionEntity);

            SessionResponse response = sessionService.updateSession(sessionId, updateRequest);

            assertNotNull(response);
            assertEquals(sessionResponse.getName(), response.getName());
            verify(sessionRepository, times(1)).findById(sessionId);
            verify(sessionRepository, times(1)).save(sessionEntity);
        }
    }

    @Test
    void testUpdateSession_NotFound() {
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

        SessionNotFoundException exception = assertThrows(SessionNotFoundException.class,
                () -> sessionService.updateSession(sessionId, updateRequest));

        assertEquals("Session not found with id: " + sessionId, exception.getMessage());
        verify(sessionRepository, times(1)).findById(sessionId);
        verify(sessionRepository, never()).save(any());
    }

    @Test
    void testDeleteSession_Success() {
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(sessionEntity));
        doNothing().when(sessionRepository).delete(sessionEntity);

        assertDoesNotThrow(() -> sessionService.deleteSession(sessionId));

        verify(sessionRepository, times(1)).findById(sessionId);
        verify(sessionRepository, times(1)).delete(sessionEntity);
    }

    @Test
    void testDeleteSession_NotFound() {
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

        SessionNotFoundException exception = assertThrows(SessionNotFoundException.class,
                () -> sessionService.deleteSession(sessionId));

        assertEquals("Session not found with id: " + sessionId, exception.getMessage());
        verify(sessionRepository, times(1)).findById(sessionId);
        verify(sessionRepository, never()).delete(any());
    }
}

