CREATE TABLE users (
    id SERIAL PRIMARY KEY
    , username VARCHAR(20) UNIQUE NOT null
    , email_address EMAIL_ADDRESS NOT null
    , first_name VARCHAR(50) NOT null
    , last_name VARCHAR(70) NOT null
    , other_names VARCHAR(120) NOT null
    , password SHA1 NOT null
    , meta_id SMALLINT UNIQUE NOT null
    , CONSTRAINT fk_meta FOREIGN KEY(meta_id) REFERENCES meta(id)
);
