package com.backend.cinema.mapper;

import org.springframework.stereotype.Component;

import com.backend.cinema.domain.User;
import com.backend.cinema.dto.UserRequest;

@Component
public class UserMapper {

	public User userRequestToUser(UserRequest userRequest) {

		return new User(userRequest.getEmail(), userRequest.getPassword());
	}
}
