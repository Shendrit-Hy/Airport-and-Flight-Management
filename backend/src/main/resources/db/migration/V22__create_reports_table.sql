CREATE TABLE reports (
    id SERIAL PRIMARY KEY,
    total_users BIGINT NOT NULL,
    total_support_requests BIGINT NOT NULL,
    total_airports BIGINT NOT NULL
);
