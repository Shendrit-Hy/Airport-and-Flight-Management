CREATE TABLE languages (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50),
    name VARCHAR(100),
    tenant_id VARCHAR(255) NOT NULL
);
