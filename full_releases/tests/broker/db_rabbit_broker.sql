
CREATE TABLE interaction(
interaction VARCHAR(20) NOT NULL,
roomCode VARCHAR(20) NOT NULL,
time VARCHAR(20) NOT NULL,
person_id VARCHAR(9) NOT NULL,
PRIMARY KEY (time)
);

CREATE TABLE current_card(
person_id VARCHAR(9) NOT NULL
);

insert into current_card(person_id) values('dummy');