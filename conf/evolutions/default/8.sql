# --- !Ups
SET SEARCH_PATH TO lunch_world;

CREATE TABLE lunch_comment (
  id         SERIAL PRIMARY KEY,
  lunch_id   INTEGER     NOT NULL REFERENCES lunch_world.lunch_tables (id),
  author_id  INTEGER     NOT NULL REFERENCES lunch_world.users (id),
  reply_to   INTEGER REFERENCES lunch_world.lunch_comment (id) DEFAULT NULL,
  created_at TIMESTAMPTZ NOT NULL                              DEFAULT now(),
  comment    TEXT        NOT NULL
);

# --- !Downs
SET SEARCH_PATH TO lunch_world;

DROP TABLE lunch_comment;
