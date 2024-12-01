package com.mukesh.models;

import java.sql.Timestamp;
import jakarta.persistence.*;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AppUser sender;

    @ManyToOne
    private Conversation conversation;

    private String content;
    private Timestamp timestamp;

    // Konstruktor domyślny
    public Message() {
    }

    // Gettery i settery

    public Long getId() {
        return id;
    }

    public AppUser getSender() {
        return sender;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSender(AppUser sender) {
        this.sender = sender;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
