CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

INSERT INTO users (username, password, role)
VALUES ('admin', '$2a$10$R/h.j.y.j.y.j.y.j.y.j.O.u.i.q.u.i.e.r.e.s.u.n.h.a.s.h', 'ADMIN');