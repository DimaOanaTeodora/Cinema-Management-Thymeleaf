package com.backend.cinema.domain;

import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "seat")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Seat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int number;

	@ManyToMany(mappedBy = "reservedSeats")
	private List<Reservation> reserved;

	@ManyToOne()
	@JoinColumn(name = "room_id", nullable = false)
	private Room room;

	public Seat(int id) {
		this.id = id;
	}

}
