package com.mukesh.service;

import java.util.List;



import com.mukesh.models.Post;


public interface PostService {
	
	Post createNewPost(Post post,Integer userId) throws Exception;
	
	String deletePost(Integer postId,Integer userid) throws Exception;
	
	List<Post>findPostByUserId(Integer userId) throws Exception;
	
	Post findPostById(Integer postId) throws Exception;
	
	List<Post> findAllPost();
	
	Post savedPost(Integer postId, Integer userId) throws Exception;

	Post toggleLikePost(Integer postId, Integer userId) throws Exception;
}
