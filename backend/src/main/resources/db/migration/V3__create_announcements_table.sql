CREATE TABLE announcements (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    message TEXT,
    created_at TIMESTAMP,
    tenant_id VARCHAR(255)
);
