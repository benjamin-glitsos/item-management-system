#!/bin/bash

PSQL_SCRIPTS="/docker-entrypoint-initdb.d/init"

psql << PSQL
$(cat $PSQL_SCRIPTS/domains.sql)

$(cat $PSQL_SCRIPTS/tables.sql)

$(eval "cat << PRE_INTERPOLATED_PSQL
$(cat $PSQL_SCRIPTS/data.sql)
PRE_INTERPOLATED_PSQL
" 2> /dev/null)

$(cat $PSQL_SCRIPTS/relationships.sql)

$(cat $PSQL_SCRIPTS/views.sql)

$(cat $PSQL_SCRIPTS/triggers.sql)
PSQL
