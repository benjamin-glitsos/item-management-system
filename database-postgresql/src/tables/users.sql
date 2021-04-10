CREATE TABLE users (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY
  , username USERNAME UNIQUE NOT NULL CHECK (username <> '')
  , email_address EMAIL_ADDRESS NOT NULL CHECK (email_address <> '')
  , first_name VARCHAR(50) NOT NULL CHECK (first_name <> '')
  , last_name VARCHAR(70) NOT NULL CHECK (last_name <> '')
  , other_names VARCHAR(120) CHECK ('' <> ANY(other_names))
  , password SHA1 NOT NULL CHECK (password <> '')
  , meta_id SMALLINT UNIQUE NOT NULL REFERENCES meta(id)
);
