package com.backend.cinema.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String email;
	private String lastName;
	private String firstName;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Reservation> reservations;

	public User() {
	}

	public User(int id, String email, String lastName, String firstName) {
		this.id = id;
		this.email = email;
		this.lastName = lastName;
		this.firstName = firstName;
	}

	public User(String email, String lastName, String firstName) {
		super();
		this.email = email;
		this.lastName = lastName;
		this.firstName = firstName;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", lastName=" + lastName + ", firstName=" + firstName + "]";
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}
