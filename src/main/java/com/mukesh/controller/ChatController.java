package com.mukesh.controller;

import java.util.List;

import com.mukesh.request.ConversationRequest;
import com.mukesh.request.MessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.mukesh.models.*;
import com.mukesh.service.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping("/conversations")
    public Conversation createConversation(@RequestHeader("Authorization") String jwt,
                                           @RequestBody ConversationRequest request) throws Exception {
        AppUser user = userService.findUserByJwt(jwt);
        List<Integer> userIds = request.getUserIds();
        if (!userIds.contains(user.getId())) {
            userIds.add(user.getId()); // Dodaj siebie do uczestników, jeśli nie został już dodany
        }
        return conversationService.createConversation(userIds, request.getName(), request.getIsGroup());
    }

    @GetMapping("/conversations")
    public List<Conversation> getUserConversations(@RequestHeader("Authorization") String jwt) throws Exception {
        AppUser user = userService.findUserByJwt(jwt);
        return conversationService.getUserConversations(user.getId());
    }

    @PostMapping("/conversations/{conversationId}/messages")
    public Message sendMessage(@RequestHeader("Authorization") String jwt,
                               @PathVariable Long conversationId,
                               @RequestBody MessageRequest request) throws Exception {
        AppUser user = userService.findUserByJwt(jwt);
        return messageService.sendMessage(conversationId, user, request.getContent());
    }

    @GetMapping("/conversations/{conversationId}/messages")
    public List<Message> getMessages(@RequestHeader("Authorization") String jwt,
                                     @PathVariable Long conversationId) throws Exception {
        AppUser user = userService.findUserByJwt(jwt);
        Conversation conversation = conversationService.getConversationById(conversationId);

        if (!conversation.getParticipants().contains(user)) {
            throw new Exception("User not part of the conversation");
        }

        return messageService.getConversationMessages(conversationId);
    }
}
