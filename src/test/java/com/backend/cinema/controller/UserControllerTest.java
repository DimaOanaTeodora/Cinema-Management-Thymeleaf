package com.backend.cinema.controller;

import com.backend.cinema.dto.UserRequest;
import com.backend.cinema.mapper.UserMapper;
import com.backend.cinema.model.User;
import com.backend.cinema.service.ReservationService;
import com.backend.cinema.service.UserService;
import com.fasterxml.jackson.databind.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.web.servlet.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class) // this tells Spring Boot to auto-configure a Spring web context
												// for integration tests for the UserController class
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private UserService userService;
	@MockBean
	private ReservationService resevrationService;
	@MockBean
	private UserMapper userMapper;

	@Test
	public void createUser() throws Exception {
		UserRequest request = new UserRequest("oanadima26@gmail.com", "Dima", "Oana-Teodora");

		when(userService.createUser(any())).thenReturn(new User(1, "oanadima26@gmail.com", "Dima", "Oana-Teodora"));

		mockMvc.perform(
				post("/users").contentType("application/json").content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.lastName").value(request.getLastName()))
				.andExpect(jsonPath("$.firstName").value(request.getFirstName()))
				.andExpect(jsonPath("$.email").value(request.getEmail()));
	}

	@Test
	public void getUser() throws Exception {
		when(userService.getUser(any())).thenReturn(new User(1, "oanadima26@gmail.com", "Dima", "Oana-Teodora"));

		mockMvc.perform(get("/users/{id}", 1).contentType("application/json")).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.lastName").value("Dima"))
				.andExpect(jsonPath("$.firstName").value("Oana-Teodora"))
				.andExpect(jsonPath("$.email").value("oanadima26@gmail.com"));

	}

	@Test
	public void deleteUser() throws Exception {

		mockMvc.perform(delete("/users/{id}", 1).contentType("application/json"))
				.andExpect(content().string("Succesfully deleted")).andExpect(status().isOk());
	}

}
