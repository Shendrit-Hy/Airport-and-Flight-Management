CREATE TABLE maintenance (
    id SERIAL PRIMARY KEY,
    airport_id VARCHAR(255),
    location VARCHAR(255),
    issue_type VARCHAR(255),
    reported_by VARCHAR(255),
    priority VARCHAR(100),
    status VARCHAR(100),
    description TEXT,
    reported_at TIMESTAMP,
    tenant_id VARCHAR(255) NOT NULL
);
