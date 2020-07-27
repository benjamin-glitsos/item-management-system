#!/bin/bash

psql << EOF

CREATE TABLE $SEX_NAME (
    id serial PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE $RECORDS_NAME (
    id serial PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    created_by SMALLINT NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    updated_by SMALLINT NOT NULL,
    deleted_at TIMESTAMP NOT NULL,
    deleted_by SMALLINT NOT NULL
);

CREATE TABLE $PEOPLE_NAME (
    id serial PRIMARY KEY,
    record_id SMALLINT NOT NULL,
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

CREATE TABLE $USERS_NAME (
    id serial PRIMARY KEY,
    person_id SMALLINT NOT NULL,
    username VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(20) NOT NULL
);

EOF
