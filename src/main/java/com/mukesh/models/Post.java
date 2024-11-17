package com.mukesh.models;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;


@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String caption;
	private String image;
	private String video;

	@ManyToOne
	private AppUser user;

	@ManyToMany
	@JoinTable(
			name = "post_likes",
			joinColumns = @JoinColumn(name = "post_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private Set<AppUser> liked = new HashSet<>();
	private LocalDateTime createdAt;

	@ManyToMany
	@JoinTable(
			name = "post_comments",
			joinColumns = @JoinColumn(name = "post_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private List<Comment> comments = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public Set<AppUser> getLiked() {
		return liked;
	}

	public void setLiked(Set<AppUser> liked) {
		this.liked = liked;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Post(Integer id, String caption, String image, String video, AppUser user, Set<AppUser> liked,
                LocalDateTime createdAt, List<Comment> comments) {
		super();
		this.id = id;
		this.caption = caption;
		this.image = image;
		this.video = video;
		this.user = user;
		this.liked = liked;
		this.createdAt = createdAt;
		this.comments = comments;
	}

	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}