package com.backend.cinema.repository.security;

import com.backend.cinema.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
    
    @Query(value = "select * from user where first_name = :name", nativeQuery = true)
	User findUserByFirstNameWithNativeQuery(String name);

	Optional<User> findByEmail(String email);
}
