INSERT INTO FloorCoverage(
  uuid, name
) VALUES(
  uuid_in(md5(now()::text)::cstring), 'artificial grass'
);
INSERT INTO FloorCoverage(
  uuid, name
) VALUES (
  uuid_in(md5(((now() + INTERVAL '1 day') + INTERVAL '1 day') :: TEXT) :: cstring), 'natural grass'
)