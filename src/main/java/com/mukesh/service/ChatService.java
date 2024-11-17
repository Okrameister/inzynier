package com.mukesh.service;

import com.mukesh.models.Message;
import com.mukesh.models.AppUser;

import java.util.List;

public interface ChatService {
    Message sendMessage(AppUser sender, AppUser receiver, String content);
    List<Message> getMessages(AppUser user1, AppUser user2);
}
