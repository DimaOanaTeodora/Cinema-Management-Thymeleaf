package com.backend.cinema.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "seat")
public class Seat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int number;

	@JsonIgnore
	@ManyToMany(mappedBy = "reservedSeats")//, cascade = CascadeType.ALL)
	private List<Reservation> reserved;

	@ManyToOne()
	@JoinColumn(name = "room_id", nullable = false)
	@JsonIgnore
	private Room room;

	public Seat() {
	}

	public Seat(int number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Seat [id=" + id + ", number=" + number + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public List<Reservation> getReserved() {
		return reserved;
	}

	public void setReserved(List<Reservation> reserved) {
		this.reserved = reserved;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

}
