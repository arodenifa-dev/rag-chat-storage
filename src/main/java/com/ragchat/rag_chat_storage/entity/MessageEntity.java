package com.ragchat.rag_chat_storage.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ragchat.rag_chat_storage.enums.SenderType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "message")
@Getter
@Setter
@NoArgsConstructor
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "message_id", updatable = false, nullable = false)
    private UUID messageId;

    // Who sent the message: USER or ASSISTANT
    @Enumerated(EnumType.STRING)
    @Column(name = "sender", nullable = false, length = 10)
    private SenderType sender;

    // Text of the message (user input or AI response)
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;
    

    // RAG retrieved context (AI only), stored as JSON
    @Column(name = "rag_context", columnDefinition = "TEXT")
    private String ragContext;

    // Metadata about the model used (AI only), stored as JSON
    @Column(name = "model_metadata", columnDefinition = "TEXT")
    private String modelMetadata;

    // Timestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Relation to Session
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private SessionEntity session;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null) createdAt = now;

    }


    
}
