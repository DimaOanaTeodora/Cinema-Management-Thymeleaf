package com.backend.cinema.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.cinema.dto.UserRequest;
import com.backend.cinema.mapper.UserMapper;
import com.backend.cinema.model.Reservation;
import com.backend.cinema.model.User;
import com.backend.cinema.service.ReservationService;
import com.backend.cinema.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

import io.swagger.annotations.*;

import org.springframework.http.*;

@RestController
@RequestMapping("/users")
@Api(value = "/users", tags = "Users")
public class UserController {

	private UserService userService;
	private ReservationService reservationService;

	private UserMapper userMapper;

	public UserController(UserService userService, ReservationService reservationService, UserMapper userMapper) {
		this.userService = userService;
		this.reservationService = reservationService;
		this.userMapper = userMapper;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Create a user", notes = "Creates a new user based on the information received in the request")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "The User was successfully created based on the received request"),
			@ApiResponse(code = 400, message = "Validation error on the received request") })
	public ResponseEntity<User> create(
			@Valid @RequestBody @ApiParam(name = "user", value = "User details", required = true) UserRequest userRequest) {
		User savedUser = userService.createUser(userMapper.userRequestToUser(userRequest));
		return ResponseEntity.created(URI.create("/users/" + savedUser.getId())).body(savedUser);
	}

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Get details for a user", notes = "Get the details for a user based on the information from the database and the user's id")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "The user was found"),
			@ApiResponse(code = 404, message = "The user was not found") })
	public ResponseEntity<User> getUser(@PathVariable Integer id) {
		return ResponseEntity.ok().body(userService.getUser(id));
	}

	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Delete a user", notes = "Delete a user by id from the database and it's reservations")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "The user was found"),
			@ApiResponse(code = 404, message = "The user was not found") })
	public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
		List<Reservation> reservations = userService.getReservationByUser(id);
		for (Reservation value : reservations) {
			reservationService.deleteReservation(value.getId());
		}

		userService.deleteUser(id);
		return ResponseEntity.ok().body("Succesfully deleted");
	}

}
