package com.backend.cinema.domain;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "broadcast")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Broadcast {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "room_id", nullable = false)
	private Room room;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "movie_id", referencedColumnName = "id")
	private Movie movie;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "schedule_id", nullable = false)
	private Schedule schedule;

	private String freeSeats;
}
