package com.backend.cinema.service;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.cinema.model.Broadcast;
import com.backend.cinema.model.Reservation;
import com.backend.cinema.model.Room;
import com.backend.cinema.model.Seat;
import com.backend.cinema.repository.SeatRepository;

@Service
public class SeatService {
	
	private SeatRepository seatRepository;

	public SeatService(SeatRepository seatRepository) {
		this.seatRepository = seatRepository;
	}

	public List<Seat> getSeatsFromList(List<Integer> seatIds) {
		List<Seat> list = new ArrayList<Seat>();
		for (Integer id : seatIds) {
			Optional<Seat> seat = seatRepository.findById(id);
			if (seat.isPresent()) {
				list.add(seat.get());
			}

		}
		return list;
	}

	public Seat createSeat(Seat seat) {
		return seatRepository.save(seat);
	}

	public Dictionary<Room, List<Seat>> getFreeSeats(List<Reservation> reservations, Broadcast broadcast) {
		Dictionary<Room, List<Seat>> dictionary = new Hashtable<Room, List<Seat>>();
		List<Seat> allSeats = seatRepository.findAll();
		List<Seat> reservedSeats = new ArrayList<Seat>();
		for (Reservation reservation : reservations) {
			reservedSeats.addAll(reservation.getReservedSeats());
		}
		for (Seat seat : allSeats) {
			if (seat.getRoom().getId() == broadcast.getRoom().getId() && !reservedSeats.contains(seat)) {
				List<Seat> l = dictionary.get(seat.getRoom());
				if (l == null) {
					l = new ArrayList<Seat>();
				}
				l.add(seat);
				dictionary.put(seat.getRoom(), l);
			}
		}
		return dictionary;
	}

	public Optional<Seat> getSeat(Integer id) {
		Optional<Seat> seatOptional = seatRepository.findById(id);
		if (seatOptional.isPresent()) {
			return seatOptional;
		} else {
			return null;
		}
	}

	public List<Seat> createSeats(Room room) {
		List<Seat> seats = new ArrayList<Seat>();
		for (int i = 1; i <= room.getCapacity(); i++) {
			Seat seat = new Seat(i);
			seat.setRoom(room);
			seats.add(createSeat(seat));
		}
		return seats;
	}
}
