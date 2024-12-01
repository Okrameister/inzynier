package com.mukesh.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mukesh.models.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByConversation_IdOrderByTimestamp(Long conversationId);
}
