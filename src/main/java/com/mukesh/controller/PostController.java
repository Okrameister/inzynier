package com.mukesh.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.mukesh.models.Post;
import com.mukesh.models.AppUser;
import com.mukesh.response.ApiResponse;
import com.mukesh.service.PostService;
import com.mukesh.service.UserService;

@RestController
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;


    @PostMapping("/api/posts")
    public ResponseEntity<Post> createPost(@RequestHeader("Authorization") String jwt,
    		@RequestBody Post post) throws Exception {
      
    	AppUser reqUser = userService.findUserByJwt(jwt);
    	Post createdPost = postService.createNewPost(post, reqUser.getId());
        return new ResponseEntity<>(createdPost, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("api/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId,
    		@RequestHeader("Authorization") String jwt) throws Exception {
    	AppUser reqUser = userService.findUserByJwt(jwt);
        String message = postService.deletePost(postId, reqUser.getId());
        ApiResponse res = new ApiResponse(message, true);
        return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
    }

    @GetMapping("api/posts/{postId}")
    public ResponseEntity<Post> findPostByHandler(@PathVariable Integer postId) throws Exception {
        Post post = postService.findPostById(postId);
        return new ResponseEntity<Post>(post, HttpStatus.ACCEPTED);
    }

    @GetMapping("api/posts/users/{userId}")
    public ResponseEntity<List<Post>> findUsersPost(@PathVariable Integer userId) throws Exception {
        List<Post> posts = postService.findPostByUserId(userId);
        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }

    @GetMapping("api/posts/group/{groupId}")
    public ResponseEntity<List<Post>> findAllPost(@PathVariable Integer groupId) {


        List<Post> posts = postService.findAllPost();

        posts.removeIf(post -> !Objects.equals(post.getGroupId(), groupId));

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }



    @PutMapping("/api/posts/like/{postId}")
    public ResponseEntity<Map<String, Object>> toggleLikePostHandler(@PathVariable Integer postId,
                                                                     @RequestHeader("Authorization") String jwt) throws Exception {
        AppUser reqUser = userService.findUserByJwt(jwt);
        Post post = postService.toggleLikePost(postId, reqUser.getId());
        boolean isLiked = post.getLiked().contains(reqUser);

        Map<String, Object> response = new HashMap<>();
        response.put("post", post);
        response.put("isLiked", isLiked);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }









}
