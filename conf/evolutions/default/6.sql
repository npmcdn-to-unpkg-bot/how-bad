# Queued schema

# --- !Ups

CREATE TABLE queued (
  movie_id char(9) NOT NULL,
  user_id int NOT NULL
);

# --- !Downs

DROP TABLE queued;
