package com.backend.cinema.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.cinema.domain.Reservation;
import com.backend.cinema.domain.security.User;
import com.backend.cinema.exception.DuplicateUserException;
import com.backend.cinema.exception.UserNotFoundException;
import com.backend.cinema.repository.security.UserRepository;


public interface UserService {

	public User createUser(User user);

	public User getUser(Integer id);

	public void deleteUser(Integer id);

	public List<Reservation> getReservationByUser(Integer id);
}
