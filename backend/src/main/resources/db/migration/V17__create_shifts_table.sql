CREATE TABLE shifts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    shift_name VARCHAR(255),
    start_time VARCHAR(50),
    end_time VARCHAR(50),
    tenant_id VARCHAR(255)
);
