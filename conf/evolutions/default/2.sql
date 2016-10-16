# --- !Ups
SET SEARCH_PATH TO lunch_world;

CREATE TABLE restaurants (
  id               SERIAL PRIMARY KEY,
  name             VARCHAR NOT NULL,
  website          VARCHAR,
  description      VARCHAR,
  added_by_user_id INTEGER REFERENCES users (id),
  CONSTRAINT uk_restaurants UNIQUE (name, website)
);


CREATE TABLE lunch_tables (
  id            SERIAL PRIMARY KEY,
  restaurant_id INTEGER     NOT NULL REFERENCES restaurants (id) ON DELETE CASCADE ON UPDATE CASCADE,
  max_size      INTEGER     NOT NULL,
  anonymous     BOOLEAN     NOT NULL,
  status        VARCHAR     NOT NULL DEFAULT 'ACTIVE',
  start_time    TIMESTAMPTZ NOT NULL
);

CREATE TABLE participants (
  lunch_table_id INTEGER NOT NULL REFERENCES lunch_tables (id) ON DELETE CASCADE ON UPDATE CASCADE,
  user_id        INTEGER NOT NULL REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
  admin          BOOLEAN NOT NULL,
  joined_on      TIMESTAMPTZ DEFAULT now(),
  CONSTRAINT pk_participants PRIMARY KEY (lunch_table_id, user_id)
);


# --- !Downs
DROP TABLE restaurants CASCADE;
DROP TABLE lunch_tables CASCADE;
DROP TABLE participants CASCADE;
