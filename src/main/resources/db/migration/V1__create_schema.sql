CREATE TABLE Address (
                id SERIAL PRIMARY KEY,
                uuid UUID,
                deleted BOOLEAN DEFAULT FALSE,
                country TEXT,
                city TEXT,
                addressLine1 TEXT,
                addressLine2 TEXT,
                zipcode TEXT
);
-- CREATE TABLE Company (
--                 id SERIAL PRIMARY KEY,
--                 uuid UUID,
--                 deleted BOOLEAN DEFAULT FALSE,
--                 name TEXT,
--                 address_id INT
-- );
-- ALTER TABLE Company
--         add constraint FK3EBA06E37BE2CBE
--         foreign key (project)
--         references Project;
