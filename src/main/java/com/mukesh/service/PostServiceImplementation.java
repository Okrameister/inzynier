package com.mukesh.service;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mukesh.models.Post;
import com.mukesh.models.User;
import com.mukesh.repository.PostRepository;
import com.mukesh.repository.UserRepository;

@Service
public class PostServiceImplementation implements PostService{
	
	@Autowired
	PostRepository postRepository ;
	
	@Autowired
	UserService userService; 
	
	@Autowired
	UserRepository userRepository;

	@Override
	public Post createNewPost(Post post, Integer userId) throws Exception {
		
		User user = userService.findUserById(userId);
	
		Post newPost = new Post();
		newPost.setCaption(post.getCaption());
		newPost.setImage(post.getImage());
		newPost.setCreatedAt(LocalDateTime.now());
		newPost.setUser(user);
		newPost.setVideo(post.getVideo());
		return postRepository.save(newPost);
	}

	@Override
	public String deletePost(Integer postId, Integer userId) throws Exception {
		Post post=findPostById(postId);
		User user = userService.findUserById(userId);
		
		if(post.getUser().getId()!=user.getId()) {
			throw new Exception("your can't delete another post");
		}
		postRepository.delete(post);
		
		return "Post Delelted";
	}

	@Override
	public List<Post> findPostByUserId(Integer userId) throws Exception {
		
		return postRepository.findPostByUserId(userId);
	}

	@Override
	public Post findPostById(Integer postId) throws Exception {
		 Optional<Post> optn = postRepository.findById(postId); 
		if(optn.isEmpty()) {
		throw new Exception("Post not found with id: "+postId);
		}
		
		return optn.get();
	}

	@Override
	public List<Post> findAllPost() {
		
		return postRepository.findAll();
	}

	@Override
	public Post savedPost(Integer postId, Integer userId) throws Exception {
		Post post=findPostById(postId);
		User user = userService.findUserById(userId);
		
		if(user.getSavedPost().contains(post)) {
			user.getSavedPost().remove(post);
		}
		else {
			user.getSavedPost().add(post);
		}
		userRepository.save(user);
		return  post;
	}

	@Override
	public Post likePost(Integer postId, Integer userId) throws Exception {
		Post post=findPostById(postId);
		User user = userService.findUserById(userId);
		
		if(post.getLiked().contains(user)) {
			post.getLiked().remove(user);
		}else {
			post.getLiked().add(user);
		}
		
		post.getLiked().add(user);
		
		
		return postRepository.save(post);
	}


}
