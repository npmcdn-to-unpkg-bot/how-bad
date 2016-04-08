# Movies schema

# --- !Ups

CREATE TABLE movie (
  imdb_id char(9) PRIMARY KEY NOT NULL
);

# --- !Downs

DROP TABLE movie;
