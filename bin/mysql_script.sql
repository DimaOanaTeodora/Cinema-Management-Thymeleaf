use cinema;

select * from broadcast;
select * from user;
select * from reservation;
select * from room;
select * from schedule;
select * from seat;
select * from movie;
select * from reserved_seat;

SET FOREIGN_KEY_CHECKS=0;
drop table broadcast;
drop table user;
drop table reservation;
drop table room;
drop table schedule;
drop table seat;
drop table movie;
drop table reserved_seat;
SET FOREIGN_KEY_CHECKS=1;