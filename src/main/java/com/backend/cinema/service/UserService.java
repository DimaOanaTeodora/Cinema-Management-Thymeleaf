package com.backend.cinema.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.cinema.exception.DuplicateUserException;
import com.backend.cinema.exception.UserNotFoundException;
import com.backend.cinema.model.Reservation;
import com.backend.cinema.model.User;
import com.backend.cinema.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User createUser(User user) {
		Optional<User> existingUserSameEmail = userRepository.findByEmail(user.getEmail());
		existingUserSameEmail.ifPresent(e -> {
			throw new DuplicateUserException();
		});
		return userRepository.save(user);
	}

	public User getUser(Integer id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			throw new UserNotFoundException(id);
		}
	}

	public void deleteUser(Integer id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()) {
			userRepository.delete(userOptional.get());
		} else {
			throw new UserNotFoundException(id);
		}
	}

	public List<Reservation> getReservationByUser(Integer id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get().getReservations();
		} else {
			throw new UserNotFoundException(id);
		}
	}
}
