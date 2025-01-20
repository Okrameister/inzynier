package com.mukesh.request;

import java.util.List;

public class ConversationRequest {
    private List<Integer> userIds;
    private String name;
    private Boolean isGroup;
    private Long eventId;

    // Gettery i settery

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

    public String getName() {
        return name;
    }

    public Boolean getIsGroup() {
        return isGroup;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsGroup(Boolean isGroup) {
        this.isGroup = isGroup;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

}
