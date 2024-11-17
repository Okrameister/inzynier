package com.mukesh.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String content;
	
	@ManyToOne
	private AppUser user;

	@ManyToMany
	private List<AppUser>liked = new ArrayList<>();
	
	private LocalDateTime createdAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public List<AppUser> getLiked() {
		return liked;
	}

	public void setLiked(List<AppUser> liked) {
		this.liked = liked;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Comment(Integer id, String content, AppUser user, List<AppUser> liked, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.content = content;
		this.user = user;
		this.liked = liked;
		this.createdAt = createdAt;
	}

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}














