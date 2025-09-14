INSERT INTO users(email, password_hash, role)
VALUES ('admin@example.com', '$2a$10$Ge8uuN.HE4J4O0hEqkLLgu1x2VwskwjwphRhVTyjcJgbiT95t5I0C', 'ADMIN');
-- a hash egy "admin" jellegű jelszó PLACEHOLDER; később cseréld!


INSERT INTO cars(licensePlate, type, numberOfPassangers, fuelType) VALUES
                                                             ('ABC-123', 'Audi', 5, 'PETROL'),
                                                             ('DEF-456', 'BMW', 4, 'PETROL'),
                                                             ('GHI-789', 'Mercedes', 7, 'DIESEL');
INSERT INTO users(email, password_hash, role)
VALUES ('user@example.com', '$2a$10$8NaAL4H4iG3c1F8iDdvuU.h71m7RhjxEiFjKV7AMACIIM8LSnIZ1W', 'USER');
-- jelszó: User123!
