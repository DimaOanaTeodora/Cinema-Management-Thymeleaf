package com.backend.cinema.dto;

import javax.validation.constraints.*;

public class UserRequest {

	@NotBlank(message = "Email cannot be null")
	private String email;

	@NotBlank(message = "Password cannot be null")
	private String password;

	public UserRequest() {

	}

	public UserRequest(@NotBlank(message = "Email cannot be null") String email,
			@NotBlank(message = "Password cannot be null") String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
