package com.backend.cinema;

import com.backend.cinema.domain.Broadcast;
import com.backend.cinema.domain.Room;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("mysql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class EntityManagerMySqlTest {

	@Autowired
	private EntityManager entityManager;

	@Test
	public void findBroadcast() {

		Broadcast broadcastFound = entityManager.find(Broadcast.class, 1);

		assertEquals(broadcastFound.getMovie().getName(), "Titanic");
	}

	@Test
	public void updateBroadcast() {

		Room roomFound = entityManager.find(Room.class, 1);

		Broadcast broadcastFound = entityManager.find(Broadcast.class, 1);
		broadcastFound.setRoom(roomFound);

		entityManager.persist(broadcastFound);

		broadcastFound = entityManager.find(Broadcast.class, 1);
		assertEquals(1, broadcastFound.getRoom().getId());

		entityManager.flush();

	}

}
