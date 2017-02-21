# --- !Ups
ALTER TABLE lunch_world.lunch_tables
  DROP COLUMN status;

ALTER TABLE lunch_world.lunch_tables
  ADD COLUMN active BOOLEAN DEFAULT TRUE;

# --- !Downs

ALTER TABLE lunch_world.lunch_tables
  DROP COLUMN active;

ALTER TABLE lunch_world.lunch_tables
  ADD COLUMN status VARCHAR DEFAULT 'ACTIVE';
