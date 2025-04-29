ALTER TABLE flights
ADD COLUMN current_latitude DECIMAL(9,6),
ADD COLUMN current_longitude DECIMAL(9,6),
ADD COLUMN altitude INTEGER,
ADD COLUMN speed INTEGER;
