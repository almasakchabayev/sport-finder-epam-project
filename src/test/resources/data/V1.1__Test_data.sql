INSERT INTO Address(
  uuid, country, city,
  addressLine1, addressLine2, zipcode
) VALUES(
  uuid_in(md5(now()::text)::cstring), 'USA', 'New York',
  '350 Fifth Avenue', '34th floor', 'NY 10118');
INSERT INTO Address(
  uuid, country, city,
  addressLine1, addressLine2, zipcode
) VALUES(
  uuid_in(md5((now() + interval '1 day')::text)::cstring), 'Canada', 'Montreal',
  '15 Bullevard', '11/5', 'MR5748');
INSERT INTO FloorCoverage(
  uuid, name
) VALUES(
  uuid_in(md5(now()::text)::cstring), 'artificial grass');
INSERT INTO FloorCoverage(
  uuid, name
) VALUES(
  uuid_in(md5(((now() + interval '1 day') + interval '1 day')::text)::cstring), 'natural grass');