# Update Review

# --- !Ups

ALTER TABLE review ADD movie text NOT NULL;

# --- !Downs

ALTER TABLE review DROP movie;
