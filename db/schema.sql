CREATE TABLE IF NOT EXISTS post
(
    id   SERIAL PRIMARY KEY,
    name TEXT
);
CREATE TABLE IF NOT EXISTS image
(
    id   SERIAL PRIMARY KEY,
    img_name TEXT
);
CREATE TABLE IF NOT EXISTS candidate
(
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    photo_id INT references image (id)
);