package com.mukesh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mukesh.models.Comment;

public interface CommentRepository  extends JpaRepository<Comment, Integer>{
	

}
