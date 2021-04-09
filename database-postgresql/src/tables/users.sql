CREATE TABLE users (
    id SERIAL PRIMARY KEY
  , username USERNAME UNIQUE NOT NULL
  , email_address EMAIL_ADDRESS NOT NULL
  , first_name VARCHAR(50) NOT NULL
  , last_name VARCHAR(70) NOT NULL
  , other_names VARCHAR(120)
  , password SHA1 NOT NULL
  , meta_id SMALLINT UNIQUE NOT NULL
  , CONSTRAINT fk_meta FOREIGN KEY(meta_id) REFERENCES meta(id)
);
