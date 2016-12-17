# --- !Ups
SET SEARCH_PATH TO lunch_world;

ALTER TABLE users
  ADD COLUMN last_login TIMESTAMPTZ DEFAULT now(),
  ADD COLUMN picture_link TEXT;


CREATE TABLE lunch_comments (
  id               SERIAL PRIMARY KEY,
  user_id          INTEGER REFERENCES users (id),
  lunch_id         INTEGER REFERENCES lunch_tables (id),
  COMMENT          TEXT        NOT NULL,
  created_at       TIMESTAMPTZ NOT NULL DEFAULT now(),
  reply_to_comment INTEGER REFERENCES lunch_comments (id)
);

# --- !Downs
SET SEARCH_PATH TO lunch_world;

ALTER TABLE users
  DROP COLUMN last_login,
  DROP COLUMN picture_link;

DROP TABLE lunch_comments;


