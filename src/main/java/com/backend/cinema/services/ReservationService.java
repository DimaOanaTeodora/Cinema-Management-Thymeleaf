package com.backend.cinema.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.cinema.domain.Broadcast;
import com.backend.cinema.domain.Reservation;
import com.backend.cinema.domain.Seat;
import com.backend.cinema.domain.security.User;


public interface ReservationService {
	
	public Reservation createReservation(Reservation reservation, User user, Broadcast broadcast, List<Seat> seats);

	public List<Reservation> getAllReservationsByBroadcast(Integer broadcastId);

	public Reservation getReservation(Integer id);

	public void deleteReservation(Integer id);
	
	public List<Reservation> getAllReservationsByUsername(String username);

}
