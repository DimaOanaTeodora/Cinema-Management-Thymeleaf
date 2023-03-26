package com.backend.cinema.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.cinema.exception.ScheduleNotFoundException;
import com.backend.cinema.model.Schedule;
import com.backend.cinema.repository.ScheduleRepository;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

	@InjectMocks
	private ScheduleService scheduleService;

	@Mock
	private ScheduleRepository scheduleRepository;

	@Test
	void whenScheduleDoesntExists_getSchedule_throwsScheduleNotFoundException() {

		// Act
		ScheduleNotFoundException exception = assertThrows(ScheduleNotFoundException.class,
				() -> scheduleService.getSchedule(1));

		// Assert
		assertEquals("Schedule with id 1 doesn't exist ", exception.getMessage());

	}

	@Test
	void updateSchedule_returnsNewTheSchedule() throws ParseException {
		// Arrange
		Schedule oldSchedule = new Schedule();
		oldSchedule.setDate(new SimpleDateFormat("dd-MM-yyyy").parse("05-07-2023"));
		oldSchedule.setEndingHour(LocalTime.of(12, 12));
		oldSchedule.setStartingHour(LocalTime.of(10, 10));
		oldSchedule.setId(1);

		Schedule newSchedule = new Schedule();
		newSchedule.setDate(new SimpleDateFormat("dd-MM-yyyy").parse("10-02-2023"));
		newSchedule.setEndingHour(LocalTime.of(11, 12));
		newSchedule.setStartingHour(LocalTime.of(10, 10));
		newSchedule.setId(1);

		when(scheduleRepository.save(oldSchedule)).thenReturn(newSchedule);

		// Act
		Schedule result = scheduleService.updateSchedule(oldSchedule, newSchedule);

		// Assert
		assertNotNull(result);
		assertEquals(oldSchedule.getId(), result.getId());

		verify(scheduleRepository).save(oldSchedule);

	}

	@Test
	void whenScheduleExists_getSchedule_returnsTheSchedule() {
		// Arrange
		Schedule schedule = new Schedule();
		schedule.setId(1);
		when(scheduleRepository.findById(1)).thenReturn(Optional.of(schedule));

		// Act
		Schedule result = scheduleService.getSchedule(1);

		// Assert
		assertNotNull(result);
		assertEquals(schedule.getId(), result.getId());
	}
}
