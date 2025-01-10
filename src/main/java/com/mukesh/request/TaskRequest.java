package com.mukesh.request;

public class TaskRequest {
    private String title;
    private String description;
    private Integer groupId;

    // Gettery i settery
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getGroupId() {return groupId;}

    public void setGroupId(Integer groupId){ this.groupId=groupId;}
}
