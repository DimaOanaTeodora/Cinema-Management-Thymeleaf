package com.backend.cinema.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.cinema.exception.RoomNotFoundException;
import com.backend.cinema.model.Room;
import com.backend.cinema.repository.RoomRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

	@InjectMocks
	private RoomService roomService;

	@Mock
	private RoomRepository roomRepository;
	
	@Test
    void room_create() {
		Room room = new Room();
        room.setName("1A");
        room.setCapacity(2);
       
        
        Room savedRoom = new Room();
        savedRoom.setName("1A");
        savedRoom.setCapacity(2);
        savedRoom.setId(1);
        when(roomRepository.save(room)).thenReturn(savedRoom);

        // Act
        Room result = roomService.createRoom(room);

        // Assert
        assertNotNull(result);
        assertEquals(savedRoom.getId(), result.getId());
        assertEquals(savedRoom.getName(), result.getName());
        assertEquals(room.getName(), result.getName());
        
        assertEquals(savedRoom.getCapacity(), result.getCapacity());
        assertEquals(room.getCapacity(), result.getCapacity());
        
        verify(roomRepository).save(room);
    }

	@Test
	void whenRoomDoesntExists_getRoom_throwsRoomNotFoundException() {

		// Act
		RoomNotFoundException exception = assertThrows(RoomNotFoundException.class, () -> roomService.getRoom(1));

		// Assert
		assertEquals("Room with id 1 doesn't exist ", exception.getMessage());

	}

	@Test
	void whenRoomExists_getRoom_returnsTheRoom() {
		// Arrange
		Room room = new Room();
		room.setId(1);
		when(roomRepository.findById(1)).thenReturn(Optional.of(room));

		// Act
		Room result = roomService.getRoom(1);

		// Assert
		assertNotNull(result);
		assertEquals(room.getId(), result.getId());
	}
}
