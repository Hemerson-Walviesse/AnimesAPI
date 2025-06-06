CREATE TABLE users (
    id text PRIMARY KEY UNIQUE  NOT NULL,
    login text not null unique,
    password text not null,
    role text not null
);