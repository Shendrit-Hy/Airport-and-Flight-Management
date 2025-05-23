CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    method VARCHAR(100),
    amount NUMERIC(19,2),
    payment_time TIMESTAMP,
    status VARCHAR(50),
    reference VARCHAR(255),
    tenant_id VARCHAR(255)
);
