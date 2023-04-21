package com.backend.cinema.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.cinema.domain.Reservation;
import com.backend.cinema.domain.security.User;
import com.backend.cinema.dto.UserRequest;
import com.backend.cinema.mapper.UserMapper;
import com.backend.cinema.repository.security.UserRepository;
import com.backend.cinema.service.ReservationService;
import com.backend.cinema.service.UserService;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

import org.springframework.http.*;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserRepository userRepository;
	
	/*private UserService userService;
	private UserRepository userRepository;
	private ReservationService reservationService;

	private UserMapper userMapper;

	public UserController(UserService userService, ReservationService reservationService, UserMapper userMapper, UserRepository userRepository) {
		this.userService = userService;
		this.reservationService = reservationService;
		this.userMapper = userMapper;
		this.userRepository = userRepository;
	}*/
	
	@PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "addUser";
        }
        
        userRepository.save(user);
        return "redirect:/index";
    }
	
	@GetMapping("/index")
	public String showUserList(Model model) {
	    model.addAttribute("users", userRepository.findAll());
	    return "index";
	}
	
	@GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "addUser";
    }

	/*@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Create a user", notes = "Creates a new user based on the information received in the request")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "The User was successfully created based on the received request"),
			@ApiResponse(code = 400, message = "Validation error on the received request") })
	public ResponseEntity<User> create(
			@Valid @RequestBody @ApiParam(name = "user", value = "User details", required = true) UserRequest userRequest) {
		User savedUser = userService.createUser(userMapper.userRequestToUser(userRequest));
		return ResponseEntity.created(URI.create("/users/" + savedUser.getId())).body(savedUser);
	}*/

	/*@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<User> getUser(@PathVariable Integer id) {
		return ResponseEntity.ok().body(userService.getUser(id));
	}

	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
		List<Reservation> reservations = userService.getReservationByUser(id);
		for (Reservation value : reservations) {
			reservationService.deleteReservation(value.getId());
		}

		userService.deleteUser(id);
		return ResponseEntity.ok().body("Succesfully deleted");
	}*/

}
