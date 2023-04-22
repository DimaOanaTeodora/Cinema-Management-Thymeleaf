package com.backend.cinema.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.backend.cinema.domain.security.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reservation")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private Integer noPersons;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Date dateRegistered;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = true)
	private User user;

	@ManyToMany(targetEntity = Seat.class)
	@JoinTable(name = "reserved_seat", joinColumns = { @JoinColumn(name = "reservation_id") }, inverseJoinColumns = {
			@JoinColumn(name = "seat_id") })
	private List<Seat> reservedSeats;

	@OneToOne
	@JoinColumn(name = "broadcast_id", referencedColumnName = "id")
	private Broadcast broadcast;

	@Transient
	private List<Integer> seats;

}
