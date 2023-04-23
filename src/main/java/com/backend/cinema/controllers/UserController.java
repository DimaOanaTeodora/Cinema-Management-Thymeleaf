package com.backend.cinema.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.cinema.configuration.Log;
import com.backend.cinema.domain.security.User;
import com.backend.cinema.repositories.security.UserRepository;
import com.backend.cinema.services.UserService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/users/")
public class UserController {

	private UserService userService;
	private UserRepository userRepository;

	@Autowired
	public UserController(UserService userService, UserRepository userRepository) {
		this.userService = userService;
		this.userRepository = userRepository;

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
		userService.create(user);

		return "redirect:list";
	}

}
