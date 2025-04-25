CREATE TABLE loyalty_programs (
    id SERIAL PRIMARY KEY,
    passenger_name VARCHAR(100) NOT NULL,
    loyalty_id VARCHAR(50) UNIQUE NOT NULL,
    points INTEGER NOT NULL,
    tier VARCHAR(20)
);
