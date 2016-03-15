# Reviews schema

# --- !Ups

CREATE SEQUENCE review_id_seq;
CREATE TABLE review (
  id integer NOT NULL DEFAULT nextval('review_id_seq'),
  comments text
);

# --- !Downs

DROP TABLE review;
DROP SEQUENCE review_id_seq;
