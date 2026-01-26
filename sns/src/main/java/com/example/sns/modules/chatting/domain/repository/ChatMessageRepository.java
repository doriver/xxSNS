package com.example.sns.modules.chatting.domain.repository;

import com.example.sns.modules.chatting.domain.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
