package com.backend.cinema.domain;


import javax.persistence.EntityManager;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;

@DataJpaTest
@ActiveProfiles("mysql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CascadeTypesTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    @Disabled
    public void saveBroadcast() throws ParseException {
        Broadcast broadcast = new Broadcast();
        
        Room room = new Room();
        room.setCapacity(5);
        room.setName("A1");
        Schedule schedule = new Schedule();
        schedule.setDate(new SimpleDateFormat("dd-MM-yyyy").parse("05-07-2023"));
        schedule.setEndingHour(LocalTime.of(12, 12));
        schedule.setStartingHour(LocalTime.of(10, 10));
        
        Movie movie = new Movie();
        movie.setName("Avatar");
        movie.setType(MovieType.D2);
        
        broadcast.setRoom(room);
        broadcast.setMovie(movie);
        broadcast.setSchedule(schedule);
       
        entityManager.persist(broadcast);
    }


    @Test
    public void updateBroadcast() {
        Broadcast broadcast = entityManager.find(Broadcast.class, 1);
        Movie movie = new Movie();
        movie.setName("Titanic");
        movie.setType(MovieType.D2);
        
        broadcast.setMovie(movie);
        
        entityManager.merge(broadcast);


        entityManager.flush();
    }

    @Test
    public void orphanRemoval() {
        Movie movie = entityManager.find(Movie.class, 1);
        entityManager.persist(movie);
        entityManager.flush();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 1})
    public void orphanRemoval(int id) {
        Movie movie = entityManager.find(Movie.class, id);
        entityManager.persist(movie);
        entityManager.flush();
    }


}
