INSERT INTO Address(
  uuid, country, city,
  addressLine1, addressLine2, zipcode
) VALUES(
  uuid_in(md5(now()::text)::cstring), 'USA', 'New York',
  '350 Fifth Avenue', '34th floor', 'NY 10118'
);
INSERT INTO Address(
  uuid, country, city,
  addressLine1, addressLine2, zipcode
) VALUES(
  uuid_in(md5((now() + interval '1 day')::text)::cstring), 'Canada', 'Montreal',
  '15 Bullevard', '11/5', 'MR5748'
);
INSERT INTO FloorCoverage(
  uuid, name
) VALUES(
  uuid_in(md5(now()::text)::cstring), 'artificial grass'
);
INSERT INTO FloorCoverage(
  uuid, name
) VALUES(
  uuid_in(md5(((now() + interval '1 day') + interval '1 day')::text)::cstring), 'natural grass'
);
INSERT INTO Sport(
  uuid, name
) VALUES(
  uuid_in(md5((now() + interval '1 day')::text)::cstring), 'football'
);
INSERT INTO Sport(
  uuid, name
) VALUES(
  uuid_in(md5(((now() + interval '1 day') + interval '1 day')::text)::cstring), 'basketball'
);
INSERT INTO PhoneNumber(
  uuid, number
) VALUES(
  uuid_in(md5(now()::text)::cstring), '87017511143'
);
INSERT INTO PhoneNumber(
  uuid, number
) VALUES(
  uuid_in(md5((now() + interval '1 day')::text)::cstring), '87017556550'
);
INSERT INTO Company(
  uuid, name, address
) VALUES(
  uuid_in(md5((now() + interval '1 day')::text)::cstring), 'Football Ltd', 1
);
INSERT INTO Company(
  uuid, name, address
) VALUES(
  uuid_in(md5(now()::text)::cstring), 'Basketball Ltd', 2
);
INSERT INTO Manager(
  uuid, firstName, lastName, email, password, company
) VALUES(
  uuid_in(md5(now()::text)::cstring), 'Peter', 'Griffin', 'pg@gmail.com', '123', 1
);
INSERT INTO Manager(
  uuid, firstName, lastName, email, password, company
) VALUES(
  uuid_in(md5((now() + interval '1 day')::text)::cstring), 'Chris', 'Griffin', 'cg@gmail.com', '1234', 2
);
INSERT INTO SportPlace(
  uuid, size, floorCoverage, capacity, indoor, changingRoom, shower, lightening, tribuneCapacity,
  otherInfrastructureFeatures, pricePerHour, description, address, company, manager
) VALUES(
  uuid_in(md5(now()::text)::cstring), '55x45', 1, 10, true, true, true, true, 1000, 'There is a parking for 100 cars',
    8000.00, 'Good air, well-placed', 1, 1, 1
);
INSERT INTO SportPlace(
  uuid, size, floorCoverage, capacity, indoor, changingRoom, shower, lightening, tribuneCapacity,
  otherInfrastructureFeatures, pricePerHour, description, address, company, manager
) VALUES(
  uuid_in(md5((now() + interval '1 day')::text)::cstring), '250 square meters', 2, 20, false, false, true, true, 0, 'There is a no parking',
  5000.00, 'Why not try', 2, 2, 2
);
INSERT INTO SportPlace_Sport(
  sport_id, sportPlace_id
) VALUES (2, 2);
INSERT INTO Company_PhoneNumber(
  company_id, PhoneNumber_id
) VALUES (2, 2);
