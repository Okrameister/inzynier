package com.mukesh.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mukesh.models.*;
import com.mukesh.repository.*;

@Service
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserRepository userRepository;

    public Conversation createConversation(List<Integer> userIds, String name, Boolean isGroup, Long eventId) throws Exception {
        Conversation conversation = new Conversation();
        conversation.setIsGroup(isGroup);
        conversation.setName(name);
        conversation.setEventId(eventId);

        List<AppUser> users = userRepository.findAllById(userIds);
        if (users.size() < userIds.size()) {
            throw new Exception("One or more users not found");
        }

        Set<AppUser> participants = new HashSet<>(users);
        conversation.setParticipants(participants);

        return conversationRepository.save(conversation);
    }

    public List<Conversation> getUserConversations(Integer userId) {
        return conversationRepository.findByParticipants_Id(userId);
    }

    public Conversation getConversationById(Long conversationId) throws Exception {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new Exception("Conversation not found"));
    }
}
