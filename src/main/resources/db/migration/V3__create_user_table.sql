CREATE TABLE users (
    id BINARY(16) PRIMARY KEY UNIQUE  NOT NULL,
    login VARCHAR(255) not null unique,
    password VARCHAR(10) not null,
    role VARCHAR(5) not null
);