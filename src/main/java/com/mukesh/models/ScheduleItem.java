package com.mukesh.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
public class ScheduleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dayOfWeek; // Dzień tygodnia (np. "Poniedziałek")
    @JsonFormat(pattern = "H:mm")
    private LocalTime startTime; // Godzina rozpoczęcia bloku

    private String subject; // Nazwa zajęć
    private String room; // Sala

    private String classType; // Typ zajęć ("Wykład", "Ćwiczenia", "Laboratoria")

    // Konstruktor domyślny
    public ScheduleItem() {
    }

    // Konstruktor z parametrami
    public ScheduleItem(String dayOfWeek, LocalTime startTime, String subject, String room, String classType) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.subject = subject;
        this.room = room;
        this.classType = classType;
    }

    // Gettery i settery

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }
}