package com.ragchat.rag_chat_storage.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ragchat.rag_chat_storage.entity.SessionEntity;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, UUID> {
}
