CREATE TABLE policy (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    content TEXT,
    type VARCHAR(100),
    created_at TIMESTAMP,
    tenant_id VARCHAR(255) NOT NULL
);
