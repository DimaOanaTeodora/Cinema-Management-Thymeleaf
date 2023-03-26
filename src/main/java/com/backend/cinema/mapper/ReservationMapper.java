package com.backend.cinema.mapper;

import org.springframework.stereotype.Component;

import com.backend.cinema.domain.Reservation;
import com.backend.cinema.dto.ReservationRequest;

@Component
public class ReservationMapper {

	public Reservation reservationRequestToReservation(ReservationRequest reservationRequest) {

		return new Reservation(reservationRequest.getNoPersons(), reservationRequest.getDateRegistered());
	}
}
