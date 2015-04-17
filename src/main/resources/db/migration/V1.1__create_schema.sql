CREATE TABLE Address (
  id SERIAL NOT NULL PRIMARY KEY,
  uuid UUID UNIQUE,
  deleted BOOLEAN DEFAULT FALSE,
  country TEXT,
  city TEXT,
  addressLine1 TEXT,
  addressLine2 TEXT,
  zipcode TEXT
);
CREATE TABLE FloorCoverage (
  id SERIAL NOT NULL PRIMARY KEY ,
  uuid UUID UNIQUE,
  deleted BOOLEAN DEFAULT FALSE,
  name TEXT UNIQUE
);
CREATE TABLE Sport (
  id SERIAL NOT NULL PRIMARY KEY,
  uuid UUID UNIQUE,
  deleted BOOLEAN DEFAULT FALSE,
  name TEXT UNIQUE
);
CREATE TABLE SportPlace (
  id SERIAL NOT NULL PRIMARY KEY,
  uuid UUID UNIQUE,
  deleted BOOLEAN DEFAULT FALSE,
  size TEXT,
  capacity INT,
  floorCoverage INT,
  indoor BOOLEAN,
  changingRoom BOOLEAN,
  shower BOOLEAN,
  lightening BOOLEAN,
  tribuneCapacity INT,
  otherInfrastructureFeatures TEXT,
  pricePerHour NUMERIC(21, 4),
  description TEXT,
  address INT
);
CREATE TABLE SportPlace_Sport (
  sportPlace_id INT NOT NULL,
  sport_id INT NOT NULL
);
ALTER TABLE SportPlace
    ADD CONSTRAINT SportPlace_FloorCoverage_fkey
    FOREIGN KEY (floorCoverage)
    REFERENCES  FloorCoverage;
ALTER TABLE SportPlace
    ADD CONSTRAINT SportPlace_Address_fkey
    FOREIGN KEY (address)
    REFERENCES  Address;