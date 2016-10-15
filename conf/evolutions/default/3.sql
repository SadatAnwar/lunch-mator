# --- !Ups
SET SEARCH_PATH TO lunch_world;

INSERT INTO acl (role) VALUES ('ADMIN'), ('USER');

# --- !Downs
SET SEARCH_PATH TO lunch_world;

DELETE FROM acl where role in ('ADMIN', 'USER');
