# Update Review

# --- !Ups

ALTER TABLE review ADD movie text NOT NULL DEFAULT '?';

# --- !Downs

ALTER TABLE review DROP movie;
