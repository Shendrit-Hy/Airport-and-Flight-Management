CREATE TABLE staff (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(255),
    role VARCHAR(255),
    email VARCHAR(255),
    shift_start TIME,
    shift_end TIME,
    tenant_id VARCHAR(255) NOT NULL
);
