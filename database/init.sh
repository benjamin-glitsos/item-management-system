#!/bin/bash

psql << EOF

-- Create tables --

CREATE TABLE $SEX_TABLE (
    id serial PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE $RECORDS_TABLE (
    id SERIAL PRIMARY KEY,
    uuid UUID UNIQUE NOT NULL,
    created_at TIMESTAMP NOT NULL,
    created_by SMALLINT NOT NULL,
    updated_at TIMESTAMP,
    updated_by SMALLINT,
    deleted_at TIMESTAMP,
    deleted_by SMALLINT
);

CREATE TABLE $PEOPLE_TABLE (
    id SERIAL PRIMARY KEY,
    record_id SMALLINT UNIQUE NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    other_names VARCHAR(255),
    sex_id SMALLINT NOT NULL,
    email_address VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    address_line_one VARCHAR(255),
    address_line_two VARCHAR(255),
    zip VARCHAR(20)
);

CREATE TABLE $USERS_TABLE (
    id SERIAL PRIMARY KEY,
    person_id SMALLINT UNIQUE NOT NULL,
    username VARCHAR(20) UNIQUE NOT NULL,
    password VARCHAR(20) NOT NULL
);

-- Populate with starting values --

INSERT INTO $SEX_TABLE (name) VALUES ('Male'), ('Female');


INSERT INTO $USERS_TABLE (
    person_id
  , username
  , password
) VALUES (
    1
  , '$SUPER_USERNAME'
  , '$SUPER_PASSWORD'
);

INSERT INTO $PEOPLE_TABLE (
    record_id
  , first_name
  , last_name
  , other_names
  , sex_id
  , email_address
  , phone_number
  , address_line_one
  , address_line_two
  , zip
) VALUES (
    1
  , '$SUPER_FIRST_NAME'
  , '$SUPER_LAST_NAME'
  , '$SUPER_MIDDLE_NAME'
  , '$SUPER_SEX'
  , '$SUPER_EMAIL'
  , '$SUPER_PHONE'
  , '$SUPER_ADDRESS_LINE_1'
  , '$SUPER_ADDRESS_LINE_2'
  , '$SUPER_ZIP'
);

INSERT INTO $RECORDS_TABLE (
    uuid
  , created_at
  , created_by
) VALUES (
    '$SUPER_UUID'
  , NOW()
  , 1
);

-- Create relationships --

ALTER TABLE $USERS_TABLE
ADD CONSTRAINT fk_${PEOPLE_TABLE}
FOREIGN KEY (person_id)
REFERENCES $PEOPLE_TABLE (id);

ALTER TABLE $PEOPLE_TABLE
ADD CONSTRAINT fk_${RECORDS_TABLE}
FOREIGN KEY (record_id)
REFERENCES $RECORDS_TABLE (id);

ALTER TABLE $RECORDS_TABLE
ADD CONSTRAINT fk_created_by
FOREIGN KEY (created_by)
REFERENCES $USERS_TABLE (id);

ALTER TABLE $RECORDS_TABLE
ADD CONSTRAINT fk_updated_by
FOREIGN KEY (updated_by)
REFERENCES $USERS_TABLE (id);

ALTER TABLE $RECORDS_TABLE
ADD CONSTRAINT fk_deleted_by
FOREIGN KEY (deleted_by)
REFERENCES $USERS_TABLE (id);

EOF
