package com.backend.cinema.services;

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
import com.backend.cinema.repositories.ReservationRepository;
import com.backend.cinema.repositories.SeatRepository;

@Service
public class SeatServiceImpl implements SeatService {

	private SeatRepository seatRepository;
	private ReservationRepository reservationRepository;

	public SeatServiceImpl(SeatRepository seatRepository, ReservationRepository reservationRepository) {
		this.seatRepository = seatRepository;
		this.reservationRepository = reservationRepository;
	}

	public List<Seat> getFreeSeatsForBrodcast(Broadcast broadcast) {
		List<Seat> listFreeSeats = new ArrayList<Seat>();
		List<Seat> allSeats = seatRepository.findAll();
		List<Seat> reservedSeats = new ArrayList<Seat>();
		List<Reservation> reservations = reservationRepository.findAll();

		// get reserved seats for a certain broadcast
		for (Reservation reservation : reservations) {
			if (reservation.getBroadcast().getId() == broadcast.getId()) {
				reservedSeats.addAll(reservation.getReservedSeats());
			}
		}

		for (Seat seat : allSeats) {
			if (seat.getRoom().getId() == broadcast.getRoom().getId() && !reservedSeats.contains(seat)) {
				listFreeSeats.add(seat);
			}
		}
		return listFreeSeats;
	}

}
