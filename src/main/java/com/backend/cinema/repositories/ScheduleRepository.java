package com.backend.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.cinema.domain.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer>{

}
