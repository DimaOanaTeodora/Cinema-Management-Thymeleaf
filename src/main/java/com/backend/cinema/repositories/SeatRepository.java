package com.backend.cinema.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.cinema.domain.Seat;

public interface SeatRepository extends JpaRepository<Seat, Integer> {

	Optional<Seat> findByNumber(int number);
}
