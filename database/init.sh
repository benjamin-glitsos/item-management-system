#!/bin/bash

PEOPLE_ROLES=${PEOPLE_TABLE}_${ROLES_TABLE}

psql << EOF

----- Create tables -----

CREATE TABLE $SEX_TABLE (
    id serial PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE $RECORDS_TABLE (
    id SERIAL PRIMARY KEY,
    uuid UUID UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    created_by SMALLINT NOT NULL,
    edits SMALLINT DEFAULT 0,
    edited_at TIMESTAMP,
    edited_by SMALLINT,
    deleted_at TIMESTAMP,
    deleted_by SMALLINT
);

CREATE TABLE $ROLES_TABLE (
    id serial PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE $PEOPLE_TABLE (
    id SERIAL PRIMARY KEY,
    record_id SMALLINT NOT NULL,
    card_number VARCHAR(12) UNIQUE NOT NULL,
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

CREATE TABLE $PEOPLE_ROLES (
    person_id SMALLINT NOT NULL,
    role_id SMALLINT NOT NULL,
    PRIMARY KEY(person_id, role_id)
);

CREATE TABLE $USERS_TABLE (
    id SERIAL PRIMARY KEY,
    record_id SMALLINT NOT NULL,
    person_id SMALLINT UNIQUE NOT NULL,
    username VARCHAR(20) UNIQUE NOT NULL,
    password VARCHAR(20) NOT NULL
);

----- Populate with starting values -----

INSERT INTO $SEX_TABLE (name) VALUES ('Male'), ('Female');

INSERT INTO $ROLES_TABLE (name)
VALUES
    ('Admin')
  , ('Staff')
  , ('Patient');

INSERT INTO $PEOPLE_TABLE (
    record_id
  , card_number
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
  , '$SUPER_CARD_NUMBER'
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

INSERT INTO $PEOPLE_ROLES (
    person_id
  , role_id
) VALUES (
    1
  , 1
);

INSERT INTO $RECORDS_TABLE (
    uuid
  , created_by
) VALUES (
    '$SUPER_UUID'
  , 1
);

INSERT INTO $USERS_TABLE (
    record_id
  , person_id
  , username
  , password
) VALUES (
    1
  , 1
  , '$SUPER_USERNAME'
  , '$SUPER_PASSWORD'
);

----- Create relationships -----

ALTER TABLE $RECORDS_TABLE
ADD CONSTRAINT fk_created_by
FOREIGN KEY (created_by)
REFERENCES $USERS_TABLE (id);

ALTER TABLE $RECORDS_TABLE
ADD CONSTRAINT fk_edited_by
FOREIGN KEY (edited_by)
REFERENCES $USERS_TABLE (id);

ALTER TABLE $RECORDS_TABLE
ADD CONSTRAINT fk_deleted_by
FOREIGN KEY (deleted_by)
REFERENCES $USERS_TABLE (id);

ALTER TABLE $PEOPLE_TABLE
ADD CONSTRAINT fk_${RECORDS_TABLE}
FOREIGN KEY (record_id)
REFERENCES $RECORDS_TABLE (id);

ALTER TABLE $PEOPLE_ROLES
ADD CONSTRAINT fk_person_id
FOREIGN KEY (person_id)
REFERENCES $PEOPLE_TABLE (id);

ALTER TABLE $PEOPLE_ROLES
ADD CONSTRAINT fk_role_id
FOREIGN KEY (role_id)
REFERENCES $ROLES_TABLE (id);

ALTER TABLE $USERS_TABLE
ADD CONSTRAINT fk_${RECORDS_TABLE}
FOREIGN KEY (record_id)
REFERENCES $RECORDS_TABLE (id);

ALTER TABLE $USERS_TABLE
ADD CONSTRAINT fk_${PEOPLE_TABLE}
FOREIGN KEY (person_id)
REFERENCES $PEOPLE_TABLE (id);

EOF
