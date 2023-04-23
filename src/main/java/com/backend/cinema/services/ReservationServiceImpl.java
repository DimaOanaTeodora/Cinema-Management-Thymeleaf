package com.backend.cinema.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	public void delete(int id) {

		Optional<Reservation> reservation = reservationRepository.findById(id);
		if (!reservation.isPresent()) {

			throw new ResourceNotFoundException("Reservation " + id + " not found");
		}
		reservationRepository.delete(reservation.get());
	}

	@Log
	public void create(Reservation reservation) {
		String username = null;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			username = authentication.getName();
		}

		Optional<User> currentUser = userRepository.findByUsername(username);
		if (!currentUser.isEmpty()) {
			reservation.setUser(currentUser.get());

		} else {
			throw new ResourceNotFoundException("You need to be logged");
		}

		reservation.setDateRegistered(new Date());

		Broadcast broadcast = reservation.getBroadcast();
		List<Seat> allSeats = broadcast.getRoom().getSeats();
		List<Seat> reservedSeats = new ArrayList<Seat>();
		for (Seat s : reservation.getReservedSeats()) {
			for (Seat ss : allSeats) {
				if (ss.getNumber() == s.getNumber()) {
					reservedSeats.add(ss);
					break;
				}
			}
		}
		reservation.setReservedSeats(reservedSeats);
		reservation.setNoPersons(reservedSeats.size());

		reservationRepository.save(reservation);
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
