package com.mukesh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mukesh.models.Comment;
import com.mukesh.models.AppUser;
import com.mukesh.service.CommentService;
import com.mukesh.service.UserService;

import java.util.List;

@RestController
public class CommentController {
	@Autowired
	private CommentService commentService;

	@Autowired
	private UserService userService;

	@PostMapping("/api/comments/post/{postId}")
	public Comment createComment(@RequestBody Comment comment, @RequestHeader("Authorization") String jwt, @PathVariable Integer postId) throws Exception {
		AppUser user = userService.findUserByJwt(jwt);
		Comment createdComment = commentService.createComment(comment, postId, user.getId());
		return createdComment;
	}

	@PutMapping("/api/comments/like/{commentId}")
	public Comment likeComment(@RequestHeader("Authorization") String jwt, @PathVariable Integer commentId) throws Exception {
		AppUser user = userService.findUserByJwt(jwt);
		Comment likedComment = commentService.likeComment(commentId, user.getId());
		return likedComment;
	}

	@GetMapping("/api/comments/post/{postId}")
	public List<Comment> getAllCommentsByPostId(@PathVariable Integer postId) throws Exception {
		return commentService.findAllCommentsByPostId(postId);
	}

	@DeleteMapping("/api/comments/{commentId}")
	public void deleteComment(@PathVariable Integer commentId) throws Exception {
		commentService.deleteCommentById(commentId);
	}
}
