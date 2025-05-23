CREATE TABLE flights (
    id SERIAL PRIMARY KEY,
    flight_number VARCHAR(255) NOT NULL,
    departure_airport VARCHAR(255),
    arrival_airport VARCHAR(255),
    departure_time TIME,
    arrival_time TIME,
    flight_date DATE,
    available_seat INT,
    price DOUBLE PRECISION,
    flight_status VARCHAR(50),
    tenant_id VARCHAR(255) NOT NULL,
    gate_id BIGINT,
    terminal_id BIGINT,
    airline_id BIGINT NOT NULL,
    CONSTRAINT fk_gate FOREIGN KEY (gate_id) REFERENCES gate(id),
    CONSTRAINT fk_terminal FOREIGN KEY (terminal_id) REFERENCES terminal(id),
    CONSTRAINT fk_airline FOREIGN KEY (airline_id) REFERENCES airline(id)
);
