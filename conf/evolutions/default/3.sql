# --- !Ups
SET SEARCH_PATH TO lunch_world;

INSERT INTO acl (role) VALUES ('ADMIN'), ('USER');

ALTER TABLE lunch_tables
  ADD COLUMN name VARCHAR;

# --- !Downs
SET SEARCH_PATH TO lunch_world;

DELETE FROM acl
WHERE role IN ('ADMIN', 'USER');

ALTER TABLE lunch_tables
  DROP COLUMN name;
