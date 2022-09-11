package com.example.model;

public class AuthenticationResponse {

	private String jwtResponse;

	public String getJwtResponse() {
		return jwtResponse;
	}

	public void setJwtResponse(String jwtResponse) {
		this.jwtResponse = jwtResponse;
	}

	public AuthenticationResponse(String jwtResponse) {
		super();
		this.jwtResponse = jwtResponse;
	}

	public AuthenticationResponse() {
		super();
	}

}