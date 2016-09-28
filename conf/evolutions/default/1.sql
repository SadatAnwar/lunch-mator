# Messages SCHEMA

# --- !Ups
CREATE SCHEMA lunch_world;

SET SEARCH_PATH TO lunch_world;

CREATE TABLE restaurants (
  id      SERIAL PRIMARY KEY,
  name    VARCHAR NOT NULL,
  website VARCHAR,
  CONSTRAINT uk_restaurants UNIQUE (name, website)
);

CREATE TABLE acl (
  id   SERIAL PRIMARY KEY,
  role VARCHAR UNIQUE
);

CREATE TABLE users (
  id         SERIAL PRIMARY KEY,
  first_name VARCHAR,
  last_name  VARCHAR,
  acl_id     INTEGER REFERENCES acl (id) ON DELETE CASCADE ON UPDATE CASCADE,
  email      VARCHAR UNIQUE,
  active     BOOLEAN DEFAULT TRUE
);

CREATE TABLE lunch_tables (
  id            SERIAL PRIMARY KEY,
  restaurant_id INTEGER REFERENCES restaurants (id) ON DELETE CASCADE ON UPDATE CASCADE,
  max_size      INTEGER,
  anonymous     BOOLEAN,
  status        VARCHAR,
  start_time    TIMESTAMPTZ
);

CREATE TABLE participants (
  lunch_table_id INTEGER REFERENCES lunch_tables (id) ON DELETE CASCADE ON UPDATE CASCADE,
  user_id        INTEGER REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
  admin          BOOLEAN,
  CONSTRAINT pk_participants PRIMARY KEY (lunch_table_id, user_id)
);

# --- !Downs
DROP SCHEMA lunch_world CASCADE;
