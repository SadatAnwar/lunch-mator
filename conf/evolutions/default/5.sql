# --- !Ups
SET SEARCH_PATH TO lunch_world;

CREATE TABLE oauth_identity (
  user_email   VARCHAR PRIMARY KEY,
  access_token VARCHAR NOT NULL,
  id_token     VARCHAR NOT NULL,
  valid_till   TIMESTAMPTZ,
  provider     VARCHAR
);

--- !Downs
SET SEARCH_PATH TO lunch_world;

DROP TABLE oauth_identity;

