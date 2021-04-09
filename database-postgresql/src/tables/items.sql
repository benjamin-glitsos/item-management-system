CREATE TABLE items (
    id SERIAL PRIMARY KEY
  , key KEY UNIQUE NOT NULL
  , name VARCHAR(250) NOT NULL
  , description TEXT
  , meta_id SMALLINT UNIQUE NOT NULL
  , CONSTRAINT fk_meta FOREIGN KEY(meta_id) REFERENCES meta(id)
);