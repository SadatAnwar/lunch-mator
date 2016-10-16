# --- !Ups
CREATE SCHEMA lunch_world;

SET SEARCH_PATH TO lunch_world;

CREATE TABLE acl (
  id   SERIAL PRIMARY KEY,
  role VARCHAR UNIQUE
);

CREATE TABLE users (
  id         SERIAL PRIMARY KEY,
  first_name VARCHAR              NOT NULL,
  last_name  VARCHAR              NOT NULL,
  email      VARCHAR UNIQUE       NOT NULL,
  active     BOOLEAN DEFAULT TRUE NOT NULL
);

CREATE TABLE user_details (
  user_id INTEGER PRIMARY KEY REFERENCES users (id)
);


CREATE TABLE user_identity (
  user_email         VARCHAR PRIMARY KEY   NOT NULL REFERENCES users (email) ON DELETE CASCADE ON UPDATE CASCADE,
  encrypted_password VARCHAR               NOT NULL,
  salt               VARCHAR               NOT NULL
);


# --- !Downs
DROP SCHEMA lunch_world CASCADE;
