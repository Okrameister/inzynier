package com.mukesh.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mukesh.config.JwtProvider;
import com.mukesh.models.AppUser;
import com.mukesh.repository.UserRepository;
@Service
public class UserServiceImplementation implements UserService{
	
	@Autowired
	UserRepository userRepository ;

	@Override
	public AppUser registerUser(AppUser user) {
		AppUser newUser= new AppUser();
//		newUser.setId(user.getId());
		newUser.setEmail(user.getEmail());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setPassword(user.getPassword());
		
		AppUser savedUser = userRepository.save(newUser);
		return savedUser;
	}

	@Override
	public AppUser findUserById(Integer userId) throws Exception {
		Optional<AppUser> user = userRepository.findById(userId);
		if (user.isPresent()) {
			return user.get();
		}
		throw new Exception("user not exist with userId" + userId);
//		return null;
	}

	@Override
	public AppUser findUserByEmail(String email) {
		AppUser user = userRepository.findByEmail(email);
		return user;
	}

	@Override
	public AppUser followUser(Integer reqUserId, Integer userId2) throws Exception {
		
		AppUser reqUser = findUserById(reqUserId);
		AppUser user2 = findUserById(userId2);
		
		user2.getFollowers().add(reqUser.getId());
		reqUser.getFollowings().add(user2.getId());
		
		userRepository.save(reqUser);
		userRepository.save(user2);
		
		return reqUser;
	}

	@Override
	public AppUser updateUser(AppUser user, Integer userId) throws Exception {
		Optional<AppUser> user1 = userRepository.findById(userId);

		if (user1.isEmpty()) {
			throw new Exception("User not exist with id: " + userId);
		}
		AppUser oldUser = user1.get();

		if (user.getFirstName() != null) {
			oldUser.setFirstName(user.getFirstName());
		}
		if (user.getLastName() != null) {
			oldUser.setLastName(user.getLastName());
		}
		if (user.getEmail() != null) {
			oldUser.setEmail(user.getEmail());
		}
//		if (user.getPassword() != null) {
//			oldUser.setPassword(user.getPassword());
//		}
		if(user.getGender() !=null) {
			oldUser.setGender(user.getGender());
		}
		
		AppUser updatedUser = userRepository.save(oldUser);
		return updatedUser;
	}

	@Override
	public List<AppUser> searchUser(String query) {
		
		
		
		return userRepository.searchUser(query);
	}

	@Override
	public AppUser findUserByJwt(String jwt) {
		String email = JwtProvider.getEmailFromJwtToken(jwt);
		AppUser user  =userRepository.findByEmail(email);
		return user;
	}
	

}
