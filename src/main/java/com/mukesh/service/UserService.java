package com.mukesh.service;

import java.util.List;

import com.mukesh.models.AppUser;

public interface UserService {
	public AppUser registerUser(AppUser user);
	public AppUser findUserById(Integer userId) throws Exception;
	public AppUser findUserByEmail(String email);
	
	public AppUser updateUser(AppUser user, Integer userId) throws Exception;
	
	public List<AppUser>searchUser(String query);
	
	public AppUser findUserByJwt(String jwt);
	public AppUser addUserGroup(Integer userId, Integer groupId) throws Exception;

    List<Integer> getUserGroup(Integer userId) throws Exception;
}
