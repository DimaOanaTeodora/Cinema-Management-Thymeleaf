package com.backend.cinema.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.cinema.domain.Broadcast;
import com.backend.cinema.domain.Reservation;
import com.backend.cinema.domain.Seat;
import com.backend.cinema.domain.security.User;
import com.backend.cinema.exception.ReservationNotFoundException;
import com.backend.cinema.repository.ReservationRepository;

@Service
public class ReservationServiceImpl implements ReservationService{
	private ReservationRepository reservationRepository;

	public ReservationServiceImpl(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	public Reservation createReservation(Reservation reservation, User user, Broadcast broadcast, List<Seat> seats) {
		reservation.setUser(user);
		reservation.setBroadcast(broadcast);
		reservation.setReservedSeats(seats);
		return reservationRepository.save(reservation);
	}

	public List<Reservation> getAllReservationsByBroadcast(Integer broadcastId) {
		return reservationRepository.findAllByBroadcastId(broadcastId);
	}

	public Reservation getReservation(Integer id) {
		Optional<Reservation> reservationOptional = reservationRepository.findById(id);
		if (reservationOptional.isPresent()) {
			return reservationOptional.get();
		} else {
			throw new ReservationNotFoundException(id);
		}
	}

	public void deleteReservation(Integer id) {
		Optional<Reservation> reservationOptional = reservationRepository.findById(id);
		if (reservationOptional.isPresent()) {
			reservationRepository.delete(reservationOptional.get());
		} else {
			throw new ReservationNotFoundException(id);
		}
	}

}
