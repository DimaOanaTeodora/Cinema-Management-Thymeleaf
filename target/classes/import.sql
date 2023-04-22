
INSERT INTO movie (name, type) VALUES ("Avatar", "D3");
INSERT INTO movie (name, type) VALUES ("Motanul incaltat", "D2");
INSERT INTO movie (name, type) VALUES ("Titanic", "D2");

INSERT INTO room (name, capacity) VALUES ("A1", 5); #room_id = 1
INSERT INTO room (name, capacity) VALUES ("A2", 3); #room_id =2

INSERT INTO seat (number, room_id) VALUES (1, 1);
INSERT INTO seat (number, room_id) VALUES (2, 1);
INSERT INTO seat (number, room_id) VALUES (3, 1);
INSERT INTO seat (number, room_id) VALUES (4, 1);
INSERT INTO seat (number, room_id) VALUES (5, 1);

INSERT INTO seat (number, room_id) VALUES (1, 2);
INSERT INTO seat (number, room_id) VALUES (2, 2);
INSERT INTO seat (number, room_id) VALUES (3, 2);

INSERT INTO schedule (date, starting_hour, ending_hour) VALUES (STR_TO_DATE("05-07-2023", "%d-%m-%Y"), "20:00", "21:00");
INSERT INTO schedule (date, starting_hour, ending_hour) VALUES (STR_TO_DATE("03-07-2023", "%d-%m-%Y"), "10:00", "11:00");
INSERT INTO schedule (date, starting_hour, ending_hour) VALUES (STR_TO_DATE("02-07-2023", "%d-%m-%Y"), "12:00", "14:00");

INSERT INTO broadcast (movie_id, room_id, schedule_id) VALUES (1, 1, 1);
INSERT INTO broadcast (movie_id, room_id, schedule_id) VALUES (2, 2, 1);

