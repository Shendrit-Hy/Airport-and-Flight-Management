CREATE TABLE security (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    guard_name VARCHAR(255),
    assigned_shift VARCHAR(255),
    tenant_id VARCHAR(255)
);
