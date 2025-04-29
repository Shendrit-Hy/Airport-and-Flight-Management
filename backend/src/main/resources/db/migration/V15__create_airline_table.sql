CREATE TABLE airlines (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    iata_code VARCHAR(10),
    country VARCHAR(100),
    tenant_id VARCHAR(255) NOT NULL,
    hub_airport VARCHAR(100)
);
