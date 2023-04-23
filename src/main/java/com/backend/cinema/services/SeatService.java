package com.backend.cinema.services;

import java.util.List;

import com.backend.cinema.domain.Broadcast;
import com.backend.cinema.domain.Seat;

public interface SeatService {

	public List<Seat> getFreeSeatsForBrodcast(Broadcast broadcast);
}
