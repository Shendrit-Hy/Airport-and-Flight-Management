CREATE TABLE maintenance (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    airport_code VARCHAR(10) NOT NULL,
    location VARCHAR(255),
    issue_type VARCHAR(255),
    reported_by VARCHAR(255),
    priority VARCHAR(50),
    status VARCHAR(50),
    description TEXT,
    reported_at TIMESTAMP
);
