package com.backend.cinema.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "broadcast")
public class Broadcast {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne // (cascade = CascadeType.ALL)
	@JoinColumn(name = "room_id", nullable = false)
	private Room room;

	@OneToOne // (cascade = CascadeType.ALL)
	@JoinColumn(name = "movie_id", referencedColumnName = "id")
	private Movie movie;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "schedule_id", nullable = false)
	private Schedule schedule;

	public Broadcast() {
	}

	public Broadcast(Schedule schedule) {
		this.schedule = schedule;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

}
