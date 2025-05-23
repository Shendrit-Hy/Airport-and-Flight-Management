CREATE TABLE parking_prices (
    id SERIAL PRIMARY KEY,
    entry_hour INT NOT NULL,
    entry_minute INT NOT NULL,
    exit_hour INT NOT NULL,
    exit_minute INT NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    tenant_id VARCHAR(255)
);
