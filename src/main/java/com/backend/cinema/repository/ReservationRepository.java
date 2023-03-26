package com.backend.cinema.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.cinema.model.Broadcast;
import com.backend.cinema.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

	@Query(nativeQuery = true, value = "select * from reservation rez where rez.broadcast_id = :id")
	List<Reservation> findAllByBroadcastId(Integer id);
	
	Optional<Reservation> findById(Integer id);
}
