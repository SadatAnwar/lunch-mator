# --- !Ups
SET SEARCH_PATH TO lunch_world;

CREATE TABLE chat_messages (
  id         SERIAL PRIMARY KEY,
  lunch_id   INTEGER     NOT NULL REFERENCES lunch_world.lunch_tables (id),
  author_id  INTEGER     NOT NULL REFERENCES lunch_world.users (id),
  reply_to   INTEGER REFERENCES lunch_world.chat_messages (id),
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  message    TEXT        NOT NULL
);

# --- !Downs
SET SEARCH_PATH TO lunch_world;

DROP TABLE chat_messages;
