package com.mukesh.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mukesh.models.AppUser;
import com.mukesh.repository.UserRepository;
import com.mukesh.service.UserService;


@RestController
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@GetMapping("/api/users")
	public List<AppUser> getUsers() {

		List<AppUser> users = userRepository.findAll();
		return users;
	}

	@GetMapping("api/users/{userId}")
	public AppUser getUsersById(@PathVariable("userId") Integer id) throws Exception {

		AppUser user = userService.findUserById(id);
		return user;
	}


	@PutMapping("/api/users")
	public AppUser updateUser(@RequestHeader("Authorization") String jwt, @RequestBody AppUser user) throws Exception {

		AppUser reqUser = userService.findUserByJwt(jwt);
		
		AppUser updatedUser = userService.updateUser(user, reqUser.getId());
		return updatedUser;
	}
	
	@PutMapping("/api/users/group/{groupId}")
    public AppUser addUserGroup (@RequestHeader("Authorization") String jwt, @PathVariable Integer groupId) throws Exception {

		AppUser reqUser = userService.findUserByJwt(jwt);
		AppUser user = userService.addUserGroup(reqUser.getId(), groupId);
		return user;
		
	}

	@GetMapping("/api/users/groups/{userId}")
	public List<Integer>getUserGroups(@PathVariable Integer userId) throws Exception {
		List<Integer> groups = userService.getUserGroup(userId);
		return groups;
	}
	
	@GetMapping("/api/users/search")
	public List<AppUser>searchUser(@RequestParam("query") String query){
		List<AppUser>users = userService.searchUser(query);
		return users;
	}
	
	@GetMapping("/api/users/profile")
	public AppUser getUserFromToken(@RequestHeader("Authorization") String jwt) {
		
//		System.out.println("jwt -------.>>"+ jwt);
		AppUser user = userService.findUserByJwt(jwt);
		user.setPassword(null);
		return user;
	}
	
	
	
	
	

}
