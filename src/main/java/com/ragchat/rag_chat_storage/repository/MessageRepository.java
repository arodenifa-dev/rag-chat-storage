package com.ragchat.rag_chat_storage.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ragchat.rag_chat_storage.entity.MessageEntity;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {

    Page<MessageEntity> findBySession_SessionIdOrderByCreatedAtAsc(
            UUID sessionId,
            Pageable pageable
    );
  
}
