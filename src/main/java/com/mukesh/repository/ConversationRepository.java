package com.mukesh.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mukesh.models.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findByParticipants_Id(Integer userId);
}
