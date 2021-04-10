CREATE TABLE users (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY
  , username USERNAME UNIQUE NOT NULL
  , email_address EMAIL_ADDRESS NOT NULL
  , first_name FIRST_NAME NOT NULL
  , last_name LAST_NAME NOT NULL
  , other_names VARCHAR(120)
  , password PASSWORD NOT NULL
  , meta_id INTEGER UNIQUE NOT NULL REFERENCES meta(id)
);
