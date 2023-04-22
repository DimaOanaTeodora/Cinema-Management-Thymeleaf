package com.backend.cinema.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.cinema.configuration.Log;
import com.backend.cinema.domain.Broadcast;
import com.backend.cinema.domain.Reservation;
import com.backend.cinema.domain.Seat;
import com.backend.cinema.domain.security.User;
import com.backend.cinema.exceptions.ResourceNotFoundException;
import com.backend.cinema.repositories.ReservationRepository;
import com.backend.cinema.repositories.security.UserRepository;

@Service
public class ReservationServiceImpl implements ReservationService {
	private ReservationRepository reservationRepository;
	private UserRepository userRepository;

	public ReservationServiceImpl(ReservationRepository reservationRepository, UserRepository userRepository) {
		this.reservationRepository = reservationRepository;
		this.userRepository = userRepository;
	}

	@Log
	public List<Reservation> getAllReservationsByUsername(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		if (!user.isEmpty()) {
			if (user.get().getReservations().size() > 0) {
				return user.get().getReservations();
			} else {
				return null;
			}
		} else {
			throw new ResourceNotFoundException("user not found");
		}
	}

}
