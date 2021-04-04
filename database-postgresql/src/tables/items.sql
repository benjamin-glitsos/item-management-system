CREATE TABLE items (
    id SERIAL PRIMARY KEY
  , name VARCHAR(250) NOT NULL
  , description TEXT NOT NULL
  , meta_id SMALLINT UNIQUE NOT NULL
  , CONSTRAINT fk_meta FOREIGN KEY(meta_id) REFERENCES meta(id)
);
