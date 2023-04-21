package com.backend.cinema.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.cinema.domain.Reservation;
import com.backend.cinema.domain.security.Authority;
import com.backend.cinema.domain.security.User;
import com.backend.cinema.repositories.security.AuthorityRepository;
import com.backend.cinema.repositories.security.UserRepository;
import com.backend.cinema.services.ReservationService;
import com.backend.cinema.services.UserService;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

@Controller
@RequestMapping("/users/")
public class UserController {

	private UserRepository userRepository;
	private AuthorityRepository authorityRepository;
    private PasswordEncoder passwordEncoder;


	@Autowired
	public UserController(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.authorityRepository = authorityRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("signup")
	public String showSignUpForm(User user) {
		return "add-user";
	}

	@GetMapping("list")
	public String showUpdateForm(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "main";
	}

	@PostMapping("add")
	public String addUser(@Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-user";
		}
		
		Authority adminRole = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());

        User newUser = User.builder()
                 .username(user.getUsername())
                 .password(passwordEncoder.encode(user.getPassword()))
                 .authority(adminRole)
                 .build();

		userRepository.save(newUser);
		return "redirect:list";
	}

	@GetMapping("edit/{username}")
	public String showUpdateForm(@PathVariable("username") String username, Model model) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Username:" + username));
		model.addAttribute("user", user);
		return "update-user";
	}

	@GetMapping("delete/{username}")
	public String deleteUser(@PathVariable("username") String username, Model model) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Username:" + username));
		userRepository.delete(user);
		model.addAttribute("users", userRepository.findAll());
		return "main";
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

	/*@GetMapping(path = "/{username}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<User> getUser(@PathVariable Integer username) {
		return ResponseEntity.ok().body(userService.getUser(username));
	}

	@DeleteMapping(path = "/{username}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> delete(@PathVariable("username") Integer username) {
		List<Reservation> reservations = userService.getReservationByUser(username);
		for (Reservation value : reservations) {
			reservationService.deleteReservation(value.getId());
		}

		userService.deleteUser(username);
		return ResponseEntity.ok().body("Succesfully deleted");
	}*/

}
