package com.mukesh.service;

import com.mukesh.models.Message;
import com.mukesh.models.AppUser;
import com.mukesh.repository.MessageRepository;
import com.mukesh.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatServiceImplementation implements ChatService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public ChatServiceImplementation(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Message sendMessage(AppUser sender, AppUser receiver, String content) {
        Message message = new Message(sender, receiver, content, LocalDateTime.now());
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessages(AppUser user1, AppUser user2) {
        return messageRepository.findBySenderOrReceiver(user1, user2);
    }
}