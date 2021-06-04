-- Entities
DROP TABLE Account CASCADE;
DROP TABLE Payment;
DROP TABLE Region CASCADE;
DROP TABLE Tag CASCADE; 
DROP TABLE Media CASCADE;

-- Weak Entities
DROP TABLE AccountUser CASCADE;
DROP TABLE Season CASCADE;
DROP TABLE Episode;
DROP TABLE Rating;

-- Relationships
DROP TABLE available_in;
DROP TABLE queues;
DROP TABLE describes;

CREATE TABLE Account (
	accid INTEGER NOT NULL,
	PRIMARY KEY (accid)
);	

CREATE TABLE Payment (
	pid INTEGER NOT NULL,
	date DATE NOT NULL,
	amount FLOAT NOT NULL,
	accid INTEGER NOT NULL,
	PRIMARY KEY(pid),
	FOREIGN KEY(accid) REFERENCES Account(accid) 
);

CREATE TABLE Region (
	regName VARCHAR(30) NOT NULL,
	PRIMARY KEY (regName)
);

CREATE TABLE Tag (
	tid INTEGER NOT NULL,
	tagName VARCHAR(30) NOT NULL,
	PRIMARY KEY (tid)
);

CREATE TABLE Media (
	mid INTEGER NOT NULL,
	title VARCHAR(30) NOT NULL,
	releaseYear INTEGER,
	isComplete BOOLEAN,
	PRIMARY KEY (mid)
);

CREATE TABLE AccountUser (
	userName VARCHAR(30) NOT NULL,
	accid INTEGER NOT NULL,
	regName VARCHAR(30) NOT NULL,
	PRIMARY KEY (userName, accid),
	FOREIGN KEY (accid) REFERENCES Account(accid),
	FOREIGN KEY (regName) REFERENCES Region(regName)
);

CREATE TABLE Season ( 
	seasonNum INTEGER NOT NULL CHECK (seasonNum > 0),
	mid INTEGER NOT NULL,
	PRIMARY KEY (seasonNum, mid),
	FOREIGN KEY (mid) REFERENCES Media(mid)
); 

CREATE TABLE Episode (
	epNum INTEGER NOT NULL CHECK (epNum > 0),
	seasonNum INTEGER NOT NULL,
	mid INTEGER NOT NULL,
	epTitle VARCHAR(30) NOT NULL,
	PRIMARY KEY (epNum, seasonNum, mid),
	FOREIGN KEY (seasonNum, mid) REFERENCES Season(seasonNum, mid)
);

CREATE TABLE Rating ( 
	mid INTEGER NOT NULL,
	userName VARCHAR(30) NOT NULL,
	accid INTEGER NOT NULL,
	value INTEGER NOT NULL CHECK (value >= 1 AND value <= 5),
	PRIMARY KEY (mid, userName, accid),
	FOREIGN KEY (mid) REFERENCES Media(mid),
	FOREIGN KEY (userName, accid) REFERENCES AccountUser(userName, accid)
);

CREATE TABLE available_in (
	mid INTEGER NOT NULL,
	regName VARCHAR(30) NOT NULL,
	PRIMARY KEY (mid, regName),
	FOREIGN KEY (mid) REFERENCES Media(mid),
	FOREIGN KEY (regName) REFERENCES Region(regName)
);

CREATE TABLE queues (
	userName VARCHAR(30) NOT NULL,
	accid INTEGER NOT NULL,
	mid INTEGER NOT NULL,
	PRIMARY KEY (userName, accid, mid),
	FOREIGN KEY (accid) REFERENCES Account(accid),
	FOREIGN KEY (mid) REFERENCES Media(mid)
);

CREATE TABLE describes ( 
	mid INTEGER NOT NULL,
	tid INTEGER NOT NULL,
	PRIMARY KEY (mid, tid),
	FOREIGN KEY (mid) REFERENCES Media(mid),
	FOREIGN KEY (tid) REFERENCES Tag(tid)
);
	
