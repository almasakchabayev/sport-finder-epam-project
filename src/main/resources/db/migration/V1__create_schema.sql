CREATE TABLE address (
                id SERIAL PRIMARY KEY,
                uuid UUID,
                deleted BOOLEAN DEFAULT FALSE,
                country TEXT,
                city TEXT,
                address_line_1 TEXT,
                address_line_2 TEXT,
                zipcode TEXT
);