package com.backend.cinema.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.cinema.domain.Reservation;
import com.backend.cinema.domain.security.User;


public interface UserService {

	public User createUser(User user);

	public User getUser(Integer id);

	public void deleteUser(Integer id);

	public List<Reservation> getReservationByUser(Integer id);
}
