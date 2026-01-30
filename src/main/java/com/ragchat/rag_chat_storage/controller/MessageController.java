package com.ragchat.rag_chat_storage.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ragchat.rag_chat_storage.dto.AddMessageRequest;
import com.ragchat.rag_chat_storage.dto.MessageResponse;
import com.ragchat.rag_chat_storage.service.MessageService;
import com.ragchat.rag_chat_storage.utils.AppLogger;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/chat")
public class MessageController {

    @Autowired
    MessageService messageService;

    private static final Logger log = AppLogger.getLogger(MessageController.class);

    @PostMapping("/session/{sessionId}/message")
    public ResponseEntity<MessageResponse> addMessage(
            @PathVariable UUID sessionId,
            @Valid @RequestBody AddMessageRequest messageRequest) {

        AppLogger.info(log, "Process add message request for session id : {}", sessionId);
        MessageResponse messageResponse = messageService.addMessage(sessionId, messageRequest);
        return ResponseEntity.ok(messageResponse);
    }

    @GetMapping("/session/{sessionId}/message")
    public ResponseEntity<Page<MessageResponse>> getMessages(
            @PathVariable UUID sessionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        AppLogger.info(log, "Process fetch all messages page wise for session id : {}", sessionId);
        return ResponseEntity.ok(
                messageService.getMessagesBySession(sessionId, page, size));
    }

}
