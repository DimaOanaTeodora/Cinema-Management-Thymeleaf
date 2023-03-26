package com.backend.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.cinema.model.Schedule;
import com.backend.cinema.model.User;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>{

}
