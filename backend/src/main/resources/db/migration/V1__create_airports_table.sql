CREATE TABLE airports (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255),
    iata_code VARCHAR(10),
    icao_code VARCHAR(10),
    country VARCHAR(100),
    city VARCHAR(100)
);
