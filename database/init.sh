#!/bin/bash

STAFF_DEPARTMENTS=${STAFF_TABLE}_${DEPARTMENTS_TABLE}

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
