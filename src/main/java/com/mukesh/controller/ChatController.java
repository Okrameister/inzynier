package com.mukesh.controller;

import com.mukesh.models.AppUser;
import com.mukesh.models.Message;
import com.mukesh.repository.UserRepository;
import com.mukesh.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;
    private final UserRepository userRepository;

    @Autowired
    public ChatController(ChatService chatService, UserRepository userRepository) {
        this.chatService = chatService;
        this.userRepository = userRepository;
    }

    @PostMapping("/send")
    public Message sendMessage(@RequestBody SendMessageRequest request) {
        // Pobieramy aktualnie zalogowanego użytkownika na podstawie emailu z requestu
        String senderEmail = request.getSenderEmail();
        AppUser sender = userRepository.findByEmail(senderEmail);
        AppUser receiver = userRepository.findByEmail("kalinka.2002@gmail.com");

        return chatService.sendMessage(sender, receiver, request.getContent());
    }

    @GetMapping("/messages")
    public List<Message> getMessages(@RequestBody GetMessagesRequest request) {
        // Pobieramy aktualnie zalogowanego użytkownika na podstawie emailu z requestu
        String userEmail = request.getUserEmail();
        AppUser user1 = userRepository.findByEmail(userEmail);
        AppUser user2 = userRepository.findByEmail(request.getOtherEmail());

        return chatService.getMessages(user1, user2);
    }

    // DTO for the send message request
    public static class SendMessageRequest {
        private String senderEmail;
        private String receiverEmail;
        private String content;

        public String getSenderEmail() {
            return senderEmail;
        }

        public void setSenderEmail(String senderEmail) {
            this.senderEmail = senderEmail;
        }

        public String getReceiverEmail() {
            return receiverEmail;
        }

        public void setReceiverEmail(String receiverEmail) {
            this.receiverEmail = receiverEmail;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    // DTO for the get messages request
    public static class GetMessagesRequest {
        private String userEmail;
        private String otherEmail;

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getOtherEmail() {
            return otherEmail;
        }

        public void setOtherEmail(String otherEmail) {
            this.otherEmail = otherEmail;
        }
    }
}
