#!/bin/bash

# Table Names:

STAFF_DEPARTMENTS=${STAFF_TABLE}_${DEPARTMENTS_TABLE}
STAFF_RECORD_INSERT=${STAFF_TABLE}_${RECORDS_TABLE}_insert
USER_RECORD_INSERT=${USERS_TABLE}_${RECORDS_TABLE}_insert

# Other Values:

SUPER_ADMIN_RECORD_NOTE="Do not edit or delete this record. It belongs to the Super Admin."

psql << EOP

$(eval "cat << EOSQL

-- Create custom functionality:

CREATE EXTENSION citext;
CREATE DOMAIN email AS citext
    CHECK ( value ~ '^[a-zA-Z0-9.!#$%&''*+/=?^_\\\`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$' );

-- Create tables:

$(cat /docker-entrypoint-initdb.d/init/tables.sql )

-- Populate with starting values:

$(cat /docker-entrypoint-initdb.d/init/starting-values.sql )

-- Create relationships:

$(cat /docker-entrypoint-initdb.d/init/relationships.sql )

EOSQL
" 2> /dev/null)

EOP
