# --- !Ups
SET SEARCH_PATH TO lunch_world;

ALTER TABLE participants
  ADD COLUMN active BOOLEAN DEFAULT TRUE NOT NULL;

# --- !Downs
SET SEARCH_PATH TO lunch_world;

ALTER TABLE participants
  DROP COLUMN active;
