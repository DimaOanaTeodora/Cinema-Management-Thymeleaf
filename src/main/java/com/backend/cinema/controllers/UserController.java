package com.backend.cinema.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.cinema.configuration.Log;
import com.backend.cinema.domain.security.Authority;
import com.backend.cinema.domain.security.User;
import com.backend.cinema.repositories.security.AuthorityRepository;
import com.backend.cinema.repositories.security.UserRepository;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/users/")
public class UserController {

	private UserRepository userRepository;
	private AuthorityRepository authorityRepository;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserController(UserRepository userRepository, AuthorityRepository authorityRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.authorityRepository = authorityRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Log
	@GetMapping("signup")
	public String showSignUpForm(User user) {
		return "add-user";
	}

	@Log
	@GetMapping("list")
	public String showUpdateForm(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "main";
	}

	@Log
	@PostMapping("add")
	public String addUser(@Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-user";
		}

		Authority adminRole = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());

		User newUser = User.builder().username(user.getUsername()).password(passwordEncoder.encode(user.getPassword()))
				.authority(adminRole).build();

		userRepository.save(newUser);
		return "redirect:list";
	}

	@Log
	@GetMapping("edit/{username}")
	public String showUpdateForm(@PathVariable("username") String username, Model model) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Username:" + username));
		model.addAttribute("user", user);
		return "update-user";
	}

	@Log
	@GetMapping("delete/{username}")
	public String deleteUser(@PathVariable("username") String username, Model model) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Username:" + username));
		userRepository.delete(user);
		model.addAttribute("users", userRepository.findAll());
		return "main";
	}

}
