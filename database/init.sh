#!/bin/bash

PSQL_SCRIPTS="/docker-entrypoint-initdb.d/init"

psql << PSQL
$(cat $PSQL_SCRIPTS/domains.sql)
$(cat $PSQL_SCRIPTS/tables.sql)
PSQL

psql << INTERPOLATED_PSQL
$(eval "cat << PSQL
$(cat $PSQL_SCRIPTS/data.sql)
PSQL
" 2> /dev/null)
INTERPOLATED_PSQL

psql << PSQL
$(cat $PSQL_SCRIPTS/relationships.sql)
$(cat $PSQL_SCRIPTS/views.sql)
$(cat $PSQL_SCRIPTS/triggers.sql)
PSQL
