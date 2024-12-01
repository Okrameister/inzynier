package com.mukesh.models;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;

@Entity
@Table(name = "conversations")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Boolean isGroup;

    @ManyToMany
    @JoinTable(
            name = "conversation_participants",
            joinColumns = @JoinColumn(name = "conversation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<AppUser> participants = new HashSet<>();

    // Konstruktor domyślny
    public Conversation() {
    }

    // Gettery i settery

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getIsGroup() {
        return isGroup;
    }

    public Set<AppUser> getParticipants() {
        return participants;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsGroup(Boolean isGroup) {
        this.isGroup = isGroup;
    }

    public void setParticipants(Set<AppUser> participants) {
        this.participants = participants;
    }
}
