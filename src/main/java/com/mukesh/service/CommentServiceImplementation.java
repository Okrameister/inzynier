package com.mukesh.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mukesh.models.Comment;
import com.mukesh.models.Post;
import com.mukesh.models.User;
import com.mukesh.repository.CommentRepository;
import com.mukesh.repository.PostRepository;

@Service
public class CommentServiceImplementation implements CommentService{
	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private CommentRepository commentRepository ;
	
	@Autowired
	private PostRepository postRepository;

	@Override
	public Comment createComment(Comment comment, Integer postId, Integer userId) throws Exception {
		
		User user= userService.findUserById(userId);
		
		Post post = postService.findPostById(postId);
		
		comment.setUser(user);
		comment.setContent(comment.getContent());
		comment.setCreatedAt(LocalDateTime.now());
		
		Comment savedComment = commentRepository.save(comment);
		
		post.getComments().add(savedComment);
		postRepository.save(post);
		
		return savedComment;
	}

	@Override
	public Comment findCommentById(Integer CommentId) throws Exception {
		Optional<Comment> opt = commentRepository.findById(CommentId);
		
		if(opt.isEmpty()) {
			throw new Exception("comment not exist");
		}
		
		return opt.get();
	}

	@Override
	public Comment likeComment(Integer CommentId, Integer userId) throws Exception {
		
		Comment comment = findCommentById(CommentId);
		
		User user= userService.findUserById(userId);
		
		if(!comment.getLiked().contains(user)) {
			comment.getLiked().add(user);
		}
		else {
			comment.getLiked().remove(user);
		}
	
		return commentRepository.save(comment);
	}
	

}
