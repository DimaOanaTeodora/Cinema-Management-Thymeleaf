package com.backend.cinema.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.cinema.domain.User;
import com.backend.cinema.exception.DuplicateUserException;
import com.backend.cinema.exception.UserNotFoundException;
import com.backend.cinema.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Test
	void whenUserAlreadyExists_create_throwsDuplicateUserException() {
		// Arrange
		User user = new User();
		user.setEmail("oanadima26@gmail.com");
		user.setPassword("1234");
		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

		// Act
		DuplicateUserException exception = assertThrows(DuplicateUserException.class,
				() -> userService.createUser(user));

		// Assert
		assertEquals("A user with the same email already exists.", exception.getMessage());
		verify(userRepository, times(0)).save(user);

	}

	@Test
	void whenUserDoesntExist_create() {
		User user = new User();
		user.setEmail("oanadima26@gmail.com");
		user.setPassword("1234");

		User savedUser = new User();
		savedUser.setEmail("oanadima26@gmail.com");
		user.setPassword("1234");
		savedUser.setId(1);

		when(userRepository.save(user)).thenReturn(savedUser);

		// Act
		User result = userService.createUser(user);

		// Assert
		assertNotNull(result);
		assertEquals(savedUser.getId(), result.getId());
		assertEquals(savedUser.getPassword(), result.getPassword());
		assertEquals(user.getPassword(), result.getPassword());

		assertEquals(savedUser.getEmail(), result.getEmail());
		assertEquals(user.getEmail(), result.getEmail());

		verify(userRepository).save(user);
	}

	@Test
	void whenUserDoesntExists_getUser_throwsUserNotFoundException() {

		// Act
		UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.getUser(1));

		// Assert
		assertEquals("User with id 1 doesn't exist ", exception.getMessage());

	}

	@Test
	void whenUserExists_getUser_returnsTheUser() {
		// Arrange
		User user = new User();
		user.setEmail("oanadima26@gmail.com");
		user.setPassword("1234");
		user.setId(1);
		when(userRepository.findById(1)).thenReturn(Optional.of(user));

		// Act
		User result = userService.getUser(1);

		// Assert
		assertNotNull(result);
		assertEquals(user.getId(), result.getId());
	}

	@Test
	public void delete_user() {
		User user = new User(1, "oanadima26@gmail.com", "1234");
		when(userRepository.findById(1)).thenReturn(Optional.of(user));

		userService.deleteUser(1);

		when(userRepository.findById(1)).thenReturn(Optional.empty());
		// Act
		UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.getUser(1));

		// Assert
		assertEquals("User with id 1 doesn't exist ", exception.getMessage());

	}

}
