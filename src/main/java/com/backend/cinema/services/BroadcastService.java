package com.backend.cinema.services;

import java.util.List;

import com.backend.cinema.domain.Broadcast;

public interface BroadcastService {

	public Broadcast create (Broadcast broadcast);
	
	public void update (Broadcast broadcast);
	
	public List<Broadcast> setBroadcastFreeSeats();

}
