# Reviews schema

# --- !Ups

CREATE SEQUENCE review_id_seq;
CREATE TABLE review (
  id integer NOT NULL DEFAULT nextval('review_id_seq'),
  user_id integer NOT NULL,
  movie text NOT NULL,
  movie_id char(9) NOT NULL,
  rating integer NOT NULL,
  comments text
);

# --- !Downs

DROP TABLE review;
DROP SEQUENCE review_id_seq;
