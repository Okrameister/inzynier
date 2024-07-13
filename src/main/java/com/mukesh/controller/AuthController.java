package com.mukesh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mukesh.config.JwtProvider;
import com.mukesh.models.User;
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
	
	
//	/auth/signup
	@PostMapping("/signup")
	public AuthResponse createUser(@RequestBody User user) throws Exception {
		
		User isExist = userRepository.findByEmail(user.getEmail());
		if(isExist!= null) {
			throw new Exception("this email already used with nother account");
		}
		
		User newUser= new User();

		newUser.setEmail(user.getEmail());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User savedUser = userRepository.save(newUser);
		
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
