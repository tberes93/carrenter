CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password_hash VARCHAR(255) NOT NULL,
                       role VARCHAR(32) NOT NULL
);


CREATE TABLE cars (
                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                           licensePlate VARCHAR(120) NOT NULL,
                           type VARCHAR(60) NOT NULL,
                           numberOfPassangers INT,
                           fuelType VARCHAR(120) not null
);


CREATE TABLE rentals (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          car_id UUID NOT NULL REFERENCES cars(id) ON DELETE CASCADE,
                          user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                          start_ts TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                          end_ts TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                          status VARCHAR(32) NOT NULL
);


CREATE INDEX idx_rentals_car_time ON rentals(car_id, start_ts, end_ts);
