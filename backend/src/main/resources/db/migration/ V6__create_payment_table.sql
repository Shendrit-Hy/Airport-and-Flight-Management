CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    method VARCHAR(50) NOT NULL,
    amount NUMERIC(10, 2) NOT NULL,
    payment_time TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    reference VARCHAR(100)
);
