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
  id SERIAL NOT NULL PRIMARY KEY,
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
  pricePerHour NUMERIC(21, 2),
  description TEXT,
  address INT,
  manager INT
);
CREATE TABLE SportPlace_Sport (
  sport_id INT NOT NULL,
  sportPlace_id INT NOT NULL,
  PRIMARY KEY (sport_id, sportPlace_id)
);
CREATE TABLE PhoneNumber (
  id SERIAL NOT NULL PRIMARY KEY,
  uuid UUID UNIQUE,
  deleted BOOLEAN DEFAULT FALSE,
  number TEXT
);
CREATE TABLE Manager (
  id SERIAL NOT NULL PRIMARY KEY,
  uuid UUID UNIQUE,
  deleted BOOLEAN DEFAULT FALSE,
  firstName TEXT,
  lastName TEXT,
  email TEXT UNIQUE,
  password TEXT,
  image INT,
  company INT
);
CREATE TABLE Image (
  id SERIAL NOT NULL PRIMARY KEY,
  uuid UUID UNIQUE,
  deleted BOOLEAN DEFAULT FALSE,
  imageArray BYTEA
);
CREATE TABLE Manager_PhoneNumber (
  manager_id INT NOT NULL,
  phoneNumber_id INT NOT NULL,
  PRIMARY KEY (manager_id, phoneNumber_id)
);
CREATE TABLE Company (
  id SERIAL NOT NULL PRIMARY KEY,
  uuid UUID UNIQUE,
  deleted BOOLEAN DEFAULT FALSE,
  name TEXT UNIQUE,
  address INT
);
ALTER TABLE SportPlace
    ADD CONSTRAINT SportPlace_FloorCoverage_fkey
    FOREIGN KEY (floorCoverage)
    REFERENCES  FloorCoverage;
ALTER TABLE SportPlace
    ADD CONSTRAINT SportPlace_Address_fkey
    FOREIGN KEY (address)
    REFERENCES  Address;
ALTER TABLE SportPlace
   ADD CONSTRAINT SportPlace_Manager_fkey
   FOREIGN KEY (manager)
   REFERENCES  Manager;
ALTER TABLE SportPlace_Sport
   ADD CONSTRAINT SportPlace_Sport_Sport_fkey
   FOREIGN KEY (sport_id)
   REFERENCES  Sport;
ALTER TABLE SportPlace_Sport
    ADD CONSTRAINT SportPlace_Sport_SportPlace_fkey
    FOREIGN KEY (sportPlace_id)
    REFERENCES  SportPlace;
ALTER TABLE Manager
   ADD CONSTRAINT Manager_Company_fkey
   FOREIGN KEY (company)
   REFERENCES  Company;
ALTER TABLE Company
   ADD CONSTRAINT Company_Address_fkey
   FOREIGN KEY (address)
   REFERENCES  Address;
ALTER TABLE Manager_PhoneNumber
   ADD CONSTRAINT Manager_PhoneNumber_Manager_fkey
   FOREIGN KEY (manager_id)
   REFERENCES  Manager;
ALTER TABLE Manager_PhoneNumber
   ADD CONSTRAINT Manager_PhoneNumber_PhoneNumber_fkey
   FOREIGN KEY (phoneNumber_id)
   REFERENCES  PhoneNumber;
