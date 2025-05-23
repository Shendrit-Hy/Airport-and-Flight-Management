CREATE TABLE currency_rate (
    code VARCHAR(10) PRIMARY KEY,
    rate_to_usd DOUBLE PRECISION,
    tenant_id VARCHAR(255) NOT NULL
);
