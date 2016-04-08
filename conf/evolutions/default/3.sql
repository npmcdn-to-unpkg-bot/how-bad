# Comments schema

# --- !Ups

CREATE SEQUENCE comment_id_seq;
CREATE TABLE comment (
  id integer NOT NULL DEFAULT nextval('comment_id_seq'),
  review_id integer NOT NULL,
  user_id integer NOT NULL,
  content text NOT NULL
);

# --- !Downs

DROP TABLE comment;
DROP SEQUENCE comment_id_seq;
