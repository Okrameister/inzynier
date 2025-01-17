package com.mukesh.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
		System.out.println(users);
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

		AppUser user = userService.findUserByJwt(jwt);
		user.setPassword(null);
		return user;
	}

	@GetMapping("/api/users/profile/{userId}")
	public ResponseEntity <AppUser> getUserFromId(
			@PathVariable String userId,
			@RequestHeader("Authorization") String authHeader) {

		try {
			Integer id = Integer.valueOf(userId);
			AppUser user = userService.findUserById(id);

			if (user == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(null); // Możesz zwrócić bardziej szczegółowy obiekt z błędem
			}

			return ResponseEntity.ok(user);
		} catch (NumberFormatException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(null); // Nieprawidłowe ID
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null); // Obsługa innych wyjątków
		}
	}


	@GetMapping("/api/users/role")
	public String getUserRoleFromToken(@RequestHeader("Authorization") String jwt) {
		AppUser user = userService.findUserByJwt(jwt);
		return user.getRole();
	}
	
	
	

}
