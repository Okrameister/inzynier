package com.mukesh.service;

import com.mukesh.models.Comment;

import java.util.List;

public interface CommentService {
	public Comment createComment(Comment comment, Integer postId, Integer userId) throws Exception;
	public Comment findCommentById (Integer CommentId) throws Exception;
	public Comment likeComment (Integer CommentId, Integer userId) throws Exception;
	public List<Comment> findAllCommentsByPostId(Integer postId) throws Exception;
	public void deleteCommentById(Integer commentId) throws Exception;
}
