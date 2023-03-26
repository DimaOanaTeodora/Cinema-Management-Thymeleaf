package com.backend.cinema.service;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.cinema.domain.Broadcast;
import com.backend.cinema.domain.Reservation;
import com.backend.cinema.domain.Room;
import com.backend.cinema.domain.Seat;
import com.backend.cinema.repository.SeatRepository;

public interface SeatService {

	public List<Seat> getSeatsFromList(List<Integer> seatIds); 

	public Seat createSeat(Seat seat) ;

	public Dictionary<Room, List<Seat>> getFreeSeats(List<Reservation> reservations, Broadcast broadcast) ;
	
	public Optional<Seat> getSeat(Integer id) ;

	public List<Seat> createSeats(Room room) ;
}
