package com.backend.cinema.dto;

import javax.validation.constraints.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "User request", description = "Required details needed to create a new user")
public class UserRequest {

	@NotBlank(message = "Email cannot be null")
	@ApiModelProperty(value = "email", required = true, notes = "The email of the user", example = "oanadima26@gmail.com", position = 1)
	private String email;

	@NotBlank(message = "Last name cannot be null")
	@ApiModelProperty(value = "lastName", required = true, notes = "The last name of the user", example = "Dima", position = 2)
	private String lastName;

	@NotBlank(message = "First name cannot be null")
	@ApiModelProperty(value = "firstName", required = true, notes = "The first name of the user", example = "Oana-Teodora", position = 3)
	private String firstName;

	public UserRequest() {

	}

	public UserRequest(@NotBlank(message = "Email cannot be null") String email,
			@NotBlank(message = "Last name cannot be null") String lastName,
			@NotBlank(message = "First name cannot be null") String firstName) {
		this.email = email;
		this.lastName = lastName;
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}
