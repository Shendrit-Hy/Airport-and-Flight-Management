CREATE TABLE staff (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    role VARCHAR(100),
    email VARCHAR(255),
    tenant_id VARCHAR(255)
);
