# Messages SCHEMA

# --- !Ups

CREATE TABLE messages (
  id      SERIAL PRIMARY KEY,
  message VARCHAR(255) NOT NULL
);

CREATE TABLE restaurants (
  id      SERIAL PRIMARY KEY,
  name    VARCHAR NOT NULL,
  website VARCHAR,
  lat     VARCHAR,
  lon     VARCHAR
);

CREATE TABLE persons (
  id          SERIAL PRIMARY KEY,
  first_name  VARCHAR NOT NULL,
  last_name   VARCHAR NOT NULL,
  description TEXT
);

CREATE TABLE tables (
  id            SERIAL PRIMARY KEY,
  lunch_time    TIMESTAMPTZ NOT NULL,
  restaurant_id INT REFERENCES restaurants (id),
  owner_id      INT REFERENCES persons (id),
  anon          BOOLEAN,
  max_size      INT DEFAULT 4
);

CREATE TABLE participants (
  table_id  INT REFERENCES tables (id),
  person_id INT REFERENCES persons (id)
);

INSERT INTO messages (message) VALUES ('Hi!');
INSERT INTO messages (message) VALUES ('What''s up?');
INSERT INTO messages (message) VALUES ('Am I alive now?');

# --- !Downs

DROP TABLE messages;
DROP TABLE restaurants;
DROP TABLE persons;
DROP TABLE tables;
DROP TABLE participants;
