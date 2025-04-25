CREATE TABLE bookings (
    id SERIAL PRIMARY KEY,
    passenger_name VARCHAR(255) NOT NULL,
    flight_number VARCHAR(50) NOT NULL,
    seat_number VARCHAR(10),
    booking_time TIMESTAMP NOT NULL,
    status VARCHAR(20)
);
