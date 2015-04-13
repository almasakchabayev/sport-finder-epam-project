CREATE TABLE address (
                id SERIAL PRIMARY KEY,
                uuid
                deleted BOOLEAN DEFAULT FALSE,
                country TEXT,
                city TEXT,
                address_line_1 TEXT,
                address_line_2 TEXT,
                zipcode TEXT
);