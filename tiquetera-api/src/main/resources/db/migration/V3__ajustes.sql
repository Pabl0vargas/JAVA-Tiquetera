-- 1. AJUSTE DE SEGURIDAD (Constraint)
-- Aseguramos a nivel de base de datos que nadie pueda guardar un recinto con capacidad negativa o cero.
-- Esto refuerza la validación que ya tenemos en Java (@Min(1)).
ALTER TABLE venues ADD CONSTRAINT chk_venue_capacity CHECK (capacity > 0);

-- 2. AJUSTE DE DATOS (Seeding)
-- Insertamos un Venue por defecto para que la base de datos no inicie vacía.
-- Esto facilita las pruebas rápidas sin tener que crear un Venue primero.
INSERT INTO venues (name, address, city, capacity)
VALUES ('Arena Inicial', 'Av. Default 100', 'Bogota', 5000);

-- Insertamos un Evento de prueba asociado a ese Venue (ID 1)
INSERT INTO events (name, event_date, category, venue_id)
VALUES ('Inauguracion Sistema', '2025-12-31 23:59:00', 'Tech', 1);