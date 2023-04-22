package com.backend.cinema.domain;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "schedule")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@JsonFormat(pattern = "HH:mm")
	private LocalTime startingHour;

	@JsonFormat(pattern = "HH:mm")
	private LocalTime endingHour;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date date;

	@OneToMany(mappedBy = "schedule")
	private List<Broadcast> broadcasts;
}
