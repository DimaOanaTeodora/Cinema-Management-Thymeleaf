package com.backend.cinema.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.cinema.domain.Reservation;
import com.backend.cinema.domain.security.User;
import com.backend.cinema.exceptions.ResourceNotFoundException;
import com.backend.cinema.repositories.security.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User createUser(User user) {
		Optional<User> existingUserSameEmail = userRepository.findByUsername(user.getUsername());
		existingUserSameEmail.ifPresent(e -> {
			throw new  ResourceNotFoundException(" Duplicate user exception ");
		});
		return userRepository.save(user);
	}

	public User getUser(Integer id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			throw new  ResourceNotFoundException(" ResourceNotFoundException ");
		}
	}

	public void deleteUser(Integer id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()) {
			userRepository.delete(userOptional.get());
		} else {
			throw new ResourceNotFoundException(" ResourceNotFoundException ");
		}
	}

	public List<Reservation> getReservationByUser(Integer id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get().getReservations();
		} else {
			throw new  ResourceNotFoundException(" ResourceNotFoundException ");
		}
	}
}
