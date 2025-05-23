CREATE TABLE faqs (
    id SERIAL PRIMARY KEY,
    question TEXT,
    answer TEXT,
    tenant_id VARCHAR(255) NOT NULL
);
