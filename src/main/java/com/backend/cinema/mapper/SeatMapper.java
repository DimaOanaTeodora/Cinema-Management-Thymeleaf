package com.backend.cinema.mapper;

import org.springframework.stereotype.Component;

import com.backend.cinema.dto.SeatRequest;
import com.backend.cinema.model.Seat;

@Component
public class SeatMapper {

	public Seat seatRequestToSeat(SeatRequest seatRequest) {

		return new Seat(seatRequest.getNumber());
	}
}
