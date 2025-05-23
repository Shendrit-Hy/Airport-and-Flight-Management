CREATE TABLE airport (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    code VARCHAR(255),
    country_id BIGINT NOT NULL,
    city_id BIGINT NOT NULL,
    timezone VARCHAR(255),
    tenant_id VARCHAR(255),
    CONSTRAINT fk_country FOREIGN KEY (country_id) REFERENCES country(id),
    CONSTRAINT fk_city FOREIGN KEY (city_id) REFERENCES city(id)
);
