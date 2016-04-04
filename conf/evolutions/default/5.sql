# Votes schema

# --- !Ups

CREATE TABLE vote (
  user_id int NOT NULL,
  review_id int NOT NULL
);

# --- !Downs

DROP TABLE vote;
