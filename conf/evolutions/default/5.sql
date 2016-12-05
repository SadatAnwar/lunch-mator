# --- !Ups
SET SEARCH_PATH TO lunch_world;

ALTER TABLE lunch_world.oauth_identity
  ADD FOREIGN KEY (user_email) REFERENCES lunch_world.users (email);

# --- !Downs
SET SEARCH_PATH TO lunch_world;

ALTER TABLE lunch_world.oauth_identity
  DROP CONSTRAINT IF EXISTS oauth_identity_user_email_fkey;

