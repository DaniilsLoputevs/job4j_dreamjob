CREATE TABLE IF NOT EXISTS post
(
    id   SERIAL PRIMARY KEY,
    name TEXT
);
CREATE TABLE IF NOT EXISTS can_img
(
    id           SERIAL PRIMARY KEY,
    byte_arr_img bytea
);
CREATE TABLE IF NOT EXISTS city
(
    id   SERIAL PRIMARY KEY,
    name varchar(200)
);
CREATE TABLE IF NOT EXISTS candidate
(
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    photo_id INT references can_img (id),
    city_id  INT references city (id)
);
CREATE TABLE IF NOT EXISTS "user"
(
    id       SERIAL PRIMARY KEY,
    name     varchar(150),
    email    varchar(100),
    password varchar(30)
);

INSERT INTO can_img(id, byte_arr_img)
VALUES (0, null);
INSERT INTO city(id, name)
VALUES (0, null);