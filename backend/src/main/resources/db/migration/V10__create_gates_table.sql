CREATE TABLE gate (
    id SERIAL PRIMARY KEY,
    gate_number VARCHAR(100),
    status VARCHAR(100),
    terminal_id BIGINT NOT NULL,
    flight_id BIGINT,
    tenant_id VARCHAR(255),
    CONSTRAINT fk_gate_terminal FOREIGN KEY (terminal_id) REFERENCES terminal(id),
    CONSTRAINT fk_gate_flight FOREIGN KEY (flight_id) REFERENCES flights(id)
);
