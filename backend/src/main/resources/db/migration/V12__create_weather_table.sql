CREATE TABLE IF NOT EXISTS weather (
    id SERIAL PRIMARY KEY,
    airport_id INTEGER NOT NULL,
    temperature VARCHAR(10) NOT NULL,
    condition VARCHAR(50) NOT NULL,
    runway_status VARCHAR(100) NOT NULL,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_airport
        FOREIGN KEY (airport_id)
        REFERENCES airports(id)
        ON DELETE CASCADE
);
