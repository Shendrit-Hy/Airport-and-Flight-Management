CREATE TABLE IF NOT EXISTS flights (
    id SERIAL PRIMARY KEY,
    flight_number VARCHAR(20) NOT NULL,
    departure_airport VARCHAR(100) NOT NULL,
    arrival_airport VARCHAR(100) NOT NULL,
    departure_time TIMESTAMP NOT NULL,
    arrival_time TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL
);
