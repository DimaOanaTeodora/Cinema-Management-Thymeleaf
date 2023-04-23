package com.backend.cinema.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.cinema.domain.Broadcast;
import com.backend.cinema.domain.Reservation;
import com.backend.cinema.domain.Seat;
import com.backend.cinema.domain.security.User;


public interface ReservationService {
	
	public void delete(int id);
	
	public void create(Reservation reservation);
	
	public List<Reservation> getAllReservationsByUsername(String username);

}
