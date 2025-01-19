package com.mukesh.response;

public class AuthResponse {

	private String token;
	private String message;
	private Integer userId;
	
	public AuthResponse() {
		// TODO Auto-generated constructor stub
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AuthResponse(String token, String message, Integer userId) {
		super();
		this.token = token;
		this.message = message;
		this.userId = userId;
	}
	
}
