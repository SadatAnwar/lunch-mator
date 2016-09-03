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

# --- !Downs
DROP SCHEMA lunch_world CASCADE;
