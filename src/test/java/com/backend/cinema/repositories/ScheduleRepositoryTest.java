package com.backend.cinema.repositories;

import com.backend.cinema.domain.Broadcast;
import com.backend.cinema.domain.Movie;
import com.backend.cinema.domain.MovieType;
import com.backend.cinema.domain.Schedule;
import com.backend.cinema.domain.Room;
import com.backend.cinema.domain.Schedule;

import lombok.extern.slf4j.Slf4j;

import org.apache.catalina.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@ActiveProfiles("mysql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Rollback(false)
@Slf4j
public class ScheduleRepositoryTest {

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Test
	@Order(1)
	public void addSchedule() throws ParseException {

		Schedule schedule = new Schedule();
		schedule.setDate(new SimpleDateFormat("dd-MM-yyyy").parse("05-07-2023"));
		schedule.setEndingHour(LocalTime.of(12, 12));
		schedule.setStartingHour(LocalTime.of(10, 10));

		scheduleRepository.save(schedule);
	}

	@Test
	@Order(2)
	public void findById() {
		Optional<Schedule> schedule = scheduleRepository.findById(1);
		assertFalse(schedule.isEmpty());
		log.info("findById ...");
		log.info(schedule.get().getDate().toString());
	}

}
