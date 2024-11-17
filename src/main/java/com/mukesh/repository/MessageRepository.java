package com.mukesh.repository;

import com.mukesh.models.Message;
import com.mukesh.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderOrReceiver(AppUser sender, AppUser receiver);
}
