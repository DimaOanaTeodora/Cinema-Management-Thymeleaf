package com.backend.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.cinema.domain.Broadcast;

public interface BroadcastRepository extends JpaRepository<Broadcast, Integer> {

}
