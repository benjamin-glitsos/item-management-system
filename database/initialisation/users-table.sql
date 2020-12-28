CREATE TABLE users (
    id SERIAL PRIMARY KEY
    , meta_id SMALLINT UNIQUE NOT null
    , username VARCHAR(20) UNIQUE NOT null
    , password SHA1 NOT null
    , email_address EMAIL_ADDRESS NOT null
    , CONSTRAINT fk_meta FOREIGN KEY(meta_id) REFERENCES meta(id)
);
