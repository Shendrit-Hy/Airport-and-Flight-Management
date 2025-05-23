CREATE TABLE bookings (
    id SERIAL PRIMARY KEY,
    passenger_name VARCHAR(255),
    passenger_id BIGINT,
    flight_number VARCHAR(100),
    seat_number VARCHAR(50),
    booking_time TIMESTAMP,
    booking_id VARCHAR(255),
    status VARCHAR(50),
    tenant_id VARCHAR(255) NOT NULL,
    checked_in BOOLEAN
);
