package com.backend.cinema.domain.security;

import lombok.*;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.backend.cinema.domain.Reservation;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class User {

	@Id
    private String username;
    
    private String password;
    
    private String email;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Reservation> reservations;

    @Singular
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn(name="authority_id", referencedColumnName = "id"))
    private Set<Authority> authorities;


    @Builder.Default
    private Boolean enabled = true;

    @Builder.Default
    private Boolean accountNotExpired = true;

    @Builder.Default
    private Boolean accountNotLocked = true;

    @Builder.Default
    private Boolean credentialsNotExpired = true;

	public User(String password, String email) {
		super();
		this.password = password;
		this.email = email;
	}

	public User(int id, String password, String email) {
		super();
		//this.id = id;
		this.password = password;
		this.email = email;
	}
    
    
}
