# Users schema

# --- !Ups

CREATE SEQUENCE account_id_seq;
CREATE TABLE account (
  id integer NOT NULL DEFAULT nextval('account_id_seq'),
  username varchar(15)
);

# --- !Downs

DROP TABLE account;
DROP SEQUENCE account_id_seq;
