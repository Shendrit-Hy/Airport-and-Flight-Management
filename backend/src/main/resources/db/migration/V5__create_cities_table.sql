CREATE TABLE cities (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    country_id BIGINT NOT NULL,
    tenant_id VARCHAR(255) NOT NULL,
    CONSTRAINT fk_cities_country FOREIGN KEY (country_id) REFERENCES country(id)
);
