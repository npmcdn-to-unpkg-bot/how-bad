# Comments schema

# --- !Ups

CREATE SEQUENCE comment_id_seq;
CREATE TABLE comment (
  id integer NOT NULL DEFAULT nextval('comment_id_seq'),
  review_id NOT NULL integer,
  user_id NOT NULL integer,
  content NOT NULL text
);

# --- !Downs

DROP TABLE comment;
DROP SEQUENCE comment_id_seq;
