package com.mukesh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.mukesh.config.JwtProvider;
import com.mukesh.models.AppUser;
import com.mukesh.repository.UserRepository;
import com.mukesh.request.LoginRequest;
import com.mukesh.response.AuthResponse;
import com.mukesh.service.CustomUserDetailsService;
import com.mukesh.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	 private UserService userService;

	@Autowired	
	private UserRepository userRepository;
	
	@Autowired	
	private PasswordEncoder passwordEncoder;
	
	@Autowired	
	private CustomUserDetailsService customUserDetails;
	

	@PostMapping("/signup")
	public AuthResponse createUser(@RequestBody AppUser user) throws Exception {
		
		AppUser isExist = userRepository.findByEmail(user.getEmail());
		if(isExist!= null) {
			throw new Exception("this email already used with nother account");
		}
		
		AppUser newUser= new AppUser();

		newUser.setEmail(user.getEmail());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		AppUser savedUser = userRepository.save(newUser);
		
		Authentication  authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
		
		String token = JwtProvider.generateToken(authentication);
		AuthResponse res = new AuthResponse(token , "Register success");
		return res;
	}
	
	@PostMapping("/signin")
	public AuthResponse signIn(@RequestBody LoginRequest loginRequest ) {
		
		Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
		String token = JwtProvider.generateToken(authentication);
		AuthResponse res = new AuthResponse(token , "Login success");
		return res;
	}

	private Authentication authenticate(String email, String password) {
		
		UserDetails  userDetails = customUserDetails.loadUserByUsername(email);
		if(userDetails== null) {
			throw new BadCredentialsException("invalid username");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Password not match");
		}
	
	
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
	
	
	
	
	
	
	
	
	
	
	

}
