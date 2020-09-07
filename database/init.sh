#!/bin/bash

# Table Names:

STAFF_DEPARTMENTS=${STAFF_TABLE}_${DEPARTMENTS_TABLE}
STAFF_RECORD_INSERT=${STAFF_TABLE}_${RECORDS_TABLE}_insert
USER_RECORD_INSERT=${USERS_TABLE}_${RECORDS_TABLE}_insert

# Other Values:

SUPER_ADMIN_RECORD_NOTE="Do not edit or delete this record. It belongs to the Super Admin."

psql << EOSQL
-- Create custom functionality:

CREATE EXTENSION citext;

CREATE DOMAIN email AS citext
CHECK (
    VALUE ~ '^[a-zA-Z0-9.!#$%&''*+/=?^_\\\`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$'
);

CREATE DOMAIN phone AS text
CHECK (
    VALUE ~ '^[0-9\\s-+()]{6,20}$'
)

CREATE DOMAIN postcode AS text
CHECK (
    VALUE ~ '^[0-9\\s-]{4}$'
)

CREATE DOMAIN hospital_number AS text
CHECK (
    VALUE ~ '^[0-9]{12}$'
)

CREATE DOMAIN medicare_number AS text
CHECK (
    VALUE ~ '^[0-9]{10}$'
)
EOSQL

psql << EOP

$(eval "cat << EOSQL

-- Create schema:

$(cat /docker-entrypoint-initdb.d/init/schema.sql)

-- Populate with starting data:

$(cat /docker-entrypoint-initdb.d/init/starting-data.sql)

-- Create relationships:

$(cat /docker-entrypoint-initdb.d/init/relationships.sql)

EOSQL
" 2> /dev/null)

EOP
