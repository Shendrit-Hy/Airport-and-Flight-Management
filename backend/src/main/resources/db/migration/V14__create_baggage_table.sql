CREATE TABLE baggage (
    id BIGINT PRIMARY KEY,
    baggage_tag VARCHAR(100) NOT NULL,
    airport_code VARCHAR(10),
    status VARCHAR(50),
    description TEXT,
    tenant_id VARCHAR(255) NOT NULL,
    last_updated TIMESTAMP
);
