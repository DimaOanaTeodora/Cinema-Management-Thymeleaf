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

public interface SeatService {

	public List<Seat> getFreeSeatsForBrodcast(Broadcast broadcast);
}
