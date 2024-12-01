package com.mukesh.service;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mukesh.models.*;
import com.mukesh.repository.*;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ConversationService conversationService;

    public Message sendMessage(Long conversationId, AppUser sender, String content) throws Exception {
        Conversation conversation = conversationService.getConversationById(conversationId);

        if (!conversation.getParticipants().contains(sender)) {
            throw new Exception("User not part of the conversation");
        }

        Message message = new Message();
        message.setConversation(conversation);
        message.setSender(sender);
        message.setContent(content);
        message.setTimestamp(new Timestamp(System.currentTimeMillis()));

        return messageRepository.save(message);
    }

    public List<Message> getConversationMessages(Long conversationId) throws Exception {
        conversationService.getConversationById(conversationId); // Sprawdź, czy konwersacja istnieje
        return messageRepository.findByConversation_IdOrderByTimestamp(conversationId);
    }
}
