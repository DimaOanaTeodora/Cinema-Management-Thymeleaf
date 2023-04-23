package com.backend.cinema.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.cinema.configuration.Log;
import com.backend.cinema.domain.Broadcast;
import com.backend.cinema.domain.Seat;
import com.backend.cinema.repositories.BroadcastRepository;

@Service
public class BroadcastServiceImpl implements BroadcastService {

	private BroadcastRepository broadcastRepository;
	private SeatService seatService;

	public BroadcastServiceImpl(BroadcastRepository broadcastRepository, SeatService seatService) {
		this.broadcastRepository = broadcastRepository;
		this.seatService = seatService;

	}

	@Log
	public Broadcast create(Broadcast broadcast) {

		return broadcastRepository.save(broadcast);
	}

	@Log
	public void update(Broadcast broadcast) {

		broadcastRepository.save(broadcast);
	}

	@Log
	public List<Broadcast> setBroadcastFreeSeats() {

		List<Broadcast> broadcasts = broadcastRepository.findAll();
		for (Broadcast broadcast : broadcasts) {
			List<Seat> seats = seatService.getFreeSeatsForBrodcast(broadcast);
			String freeSeats = "";
			for (Seat s : seats) {
				freeSeats = freeSeats + s.getNumber() + "; ";
			}
			broadcast.setFreeSeats(freeSeats);
		}
		return broadcasts;
	}

}
