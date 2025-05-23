CREATE TABLE passengers (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(50),
    age BIGINT,
    tenant_id VARCHAR(255)
);
