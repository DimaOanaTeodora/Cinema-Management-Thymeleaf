package com.backend.cinema.model;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "schedule")
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
	@JsonIgnore
	private List<Broadcast> broadcasts;

	public Schedule() {
	}

	public Schedule(LocalTime startingHour, LocalTime endingHour, Date date) {
		this.startingHour = startingHour;
		this.endingHour = endingHour;
		this.date = date;
	}

	@Override
	public String toString() {
		return "Schedule [id=" + id + ", startingHour=" + startingHour + ", endingHour=" + endingHour + ", date=" + date
				+ "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalTime getStartingHour() {
		return startingHour;
	}

	public void setStartingHour(LocalTime startingHour) {
		this.startingHour = startingHour;
	}

	public LocalTime getEndingHour() {
		return endingHour;
	}

	public void setEndingHour(LocalTime endingHour) {
		this.endingHour = endingHour;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Broadcast> getBroadcasts() {
		return broadcasts;
	}

	public void setBroadcasts(List<Broadcast> broadcasts) {
		this.broadcasts = broadcasts;
	}

}
