package com.backend.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.cinema.domain.Schedule;
import com.backend.cinema.domain.User;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>{

}
