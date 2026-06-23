CREATE DATABASE jogoseducativos OWNER postgres;
GRANT ALL PRIVILEGES ON DATABASE jogoseducativos TO postgres;

\connect jogoseducativos

CREATE TABLE IF NOT EXISTS players (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    amount DOUBLE PRECISION NOT NULL
);
