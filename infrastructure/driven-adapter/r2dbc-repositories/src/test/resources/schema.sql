CREATE TABLE posts
(
    id      serial PRIMARY KEY,
    title   varchar(64) UNIQUE NOT NULL,
    content text               NOT NULL
);
