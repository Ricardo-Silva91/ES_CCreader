CREATE TABLE person(
country VARCHAR(20) NOT NULL,
firstname VARCHAR(50) NOT NULL,
notes VARCHAR(500) NOT NULL,
documentType VARCHAR(50) NOT NULL,
cardVersion VARCHAR(50) NOT NULL,
numBI VARCHAR(9) NOT NULL,
lastnameFather VARCHAR(50) NOT NULL,
lastnameMother VARCHAR(50) NOT NULL,
numSNS VARCHAR(9) NOT NULL,
firstnameMother VARCHAR(20) NOT NULL,
locale VARCHAR(50) NOT NULL,
deliveryDate VARCHAR(15) NOT NULL,
height VARCHAR(6) NOT NULL,
numSS VARCHAR(11) NOT NULL,
sex VARCHAR(1) NOT NULL,
cardNumberPAN VARCHAR(16) NOT NULL,
firstnameFather VARCHAR(20) NOT NULL,
birthDate VARCHAR(15) NOT NULL,
mrz3 VARCHAR(50) NOT NULL,
mrz2 VARCHAR(50) NOT NULL,
lastname VARCHAR(50) NOT NULL,
mrz1 VARCHAR(50) NOT NULL,
nationality VARCHAR(5) NOT NULL,
numNIF VARCHAR(9) NOT NULL,
cardNumber VARCHAR(25) NOT NULL,
deliveryEntity VARCHAR(50) NOT NULL,
Authentication VARCHAR(10), 
PRIMARY KEY (numBI));


CREATE TABLE interaction(
interaction VARCHAR(20) NOT NULL,
roomCode VARCHAR(20) NOT NULL,
time VARCHAR(20) NOT NULL,
person_id VARCHAR(9) NOT NULL,
PRIMARY KEY (time),
FOREIGN KEY (person_id)
	REFERENCES person (numBI)
	ON UPDATE CASCADE
	ON DELETE CASCADE);
    
CREATE TABLE current_card(
person_id VARCHAR(9) NOT NULL,
PRIMARY KEY (person_id),
FOREIGN KEY (person_id)
	REFERENCES person (numBI)
	ON UPDATE CASCADE
	ON DELETE CASCADE);
    
INSERT INTO `person` (`country`, `firstname`, `notes`, `documentType`, `cardVersion`, `numBI`, `lastnameFather`, `lastnameMother`, `numSNS`, `firstnameMother`, `locale`, `deliveryDate`, `height`, `numSS`, `sex`, `cardNumberPAN`, `firstnameFather`, `birthDate`, `mrz3`, `mrz2`, `lastname`, `mrz1`, `nationality`, `numNIF`, `cardNumber`, `deliveryEntity`, `Authentication`) VALUES ('d', 'dummy', 'dummy', 'dummy', 'dummy', 'dummy', 'dummy', 'dummy', 'dummy', 'dummy', 'dummy', 'dummy', 'dummy', 'dummy', 'd', 'dummy', 'dummy', 'dummy', 'dummy', 'dummy', 'dummy', 'dummy', 'dummy', 'dummy', 'dummy', 'dummy', 'dummy');
INSERT INTO `current_card` (`person_id`) VALUES ('dummy');
