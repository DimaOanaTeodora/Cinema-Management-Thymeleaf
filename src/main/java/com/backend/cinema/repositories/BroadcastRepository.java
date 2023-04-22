package com.backend.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.cinema.domain.Broadcast;

@Repository
public interface BroadcastRepository extends JpaRepository<Broadcast, Integer> {

}
