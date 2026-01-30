package com.ragchat.rag_chat_storage.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ragchat.rag_chat_storage.dto.CreateSessionRequest;
import com.ragchat.rag_chat_storage.dto.SessionResponse;
import com.ragchat.rag_chat_storage.dto.UpdateSessionRequest;
import com.ragchat.rag_chat_storage.service.SessionService;
import com.ragchat.rag_chat_storage.utils.AppLogger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
 import jakarta.validation.Valid;

@Tag(name = "Session API", description = "Manage rag chat sessions")
@RestController
@RequestMapping("/api/v1/chat")
public class SessionController {

    @Autowired
    SessionService sessionService;

    private static final Logger log = AppLogger.getLogger(SessionController.class);

    @Operation(
        summary = "Create a new chat session",
        description = "Creates a new RAG chat session for a user"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Session created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/session")
    public ResponseEntity<SessionResponse> createSession(
            @RequestBody @Valid CreateSessionRequest request) {

        AppLogger.info(log, "Process create session request for user : {}", request.getUserId());
        SessionResponse session = sessionService.createSession(request);
        return new ResponseEntity<>(session, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Updates the existing chat session",
        description = "Updates the existing RAG chat session using session id"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Session created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PutMapping("/session/{sessionId}")
    public ResponseEntity<SessionResponse> updateSession(
            @PathVariable UUID sessionId,
            @Valid @RequestBody UpdateSessionRequest request) {

        AppLogger.info(log, "Process update session request for session id : {}", sessionId);
        SessionResponse sessionResponse = sessionService.updateSession(sessionId, request);
        return ResponseEntity.ok(sessionResponse);
    }

    // DELETE session by ID
    @Operation(
        summary = "deletes the chat session",
        description = "Deletes the existing RAG chat session using session id"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Session created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @DeleteMapping("/session/{sessionId}")
    public ResponseEntity<Void> deleteSession(@PathVariable UUID sessionId) {

        AppLogger.info(log, "Process delete session request for session id : {}", sessionId);
        sessionService.deleteSession(sessionId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}
