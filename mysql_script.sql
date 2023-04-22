use cinema;

select * from broadcast;
select * from user;
select * from reservation;
select * from room;
select * from schedule;
select * from seat;
select * from movie;
select * from reserved_seat;

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

INSERT INTO schedule (date, ending_hour, starting_hour) VALUES (STR_TO_DATE("05-07-2023", "%m-%d-%Y"), "20:00", "21:00");
INSERT INTO schedule (date, ending_hour, starting_hour) VALUES (STR_TO_DATE("03-07-2023", "%m-%d-%Y"), "10:00", "11:00");
INSERT INTO schedule (date, ending_hour, starting_hour) VALUES (STR_TO_DATE("02-07-2023", "%m-%d-%Y"), "12:00", "14:00");

INSERT INTO broadcast (movie_id, room_id, schedule_id) VALUES (1, 1, 1);
INSERT INTO broadcast (movie_id, room_id, schedule_id) VALUES (2, 2, 1);

SET FOREIGN_KEY_CHECKS=0;
drop table broadcast;
drop table user;
drop table reservation;
drop table room;
drop table schedule;
drop table seat;
drop table movie;
drop table reserved_seat;

drop table product;
drop table participant;
drop table info;
drop table user_authority;
drop table product_category;
drop table authoroty;
SET FOREIGN_KEY_CHECKS=1;