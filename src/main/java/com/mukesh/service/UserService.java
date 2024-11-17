package com.mukesh.service;

import java.util.List;

import com.mukesh.models.AppUser;

public interface UserService {
	public AppUser registerUser(AppUser user);
	public AppUser findUserById(Integer userId) throws Exception;
	public AppUser findUserByEmail(String email);
	
	public AppUser followUser(Integer user1, Integer userId2) throws Exception;
	
	public AppUser updateUser(AppUser user, Integer userId) throws Exception;
	
	public List<AppUser>searchUser(String query);
	
	public AppUser findUserByJwt(String jwt);

	

}
