-- Índices para acelerar búsquedas por ciudad y categoría
CREATE INDEX idx_events_category ON events(category);
CREATE INDEX idx_venues_city ON venues(city);