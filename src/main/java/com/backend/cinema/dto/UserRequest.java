package com.backend.cinema.dto;

import javax.validation.constraints.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "User request", description = "Required details needed to create a new user")
public class UserRequest {

	@NotBlank(message = "Email cannot be null")
	@ApiModelProperty(value = "email", required = true, notes = "The email of the user", example = "oanadima26@gmail.com", position = 1)
	private String email;

	@NotBlank(message = "Password cannot be null")
	@ApiModelProperty(value = "password", required = true, notes = "The password of the user", example = "*12356T", position = 2)
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
