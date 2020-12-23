#!/bin/bash

PSQL_INIT="/docker-entrypoint-initdb.d/initialisation"

psql << PSQL
$(cat $PSQL_INIT/domains.sql)

$(cat $PSQL_INIT/query-functions.sql)

$(cat $PSQL_INIT/tables.sql)

$(eval "cat << PRE_INTERPOLATED_PSQL
$(cat $PSQL_INIT/data.sql)
PRE_INTERPOLATED_PSQL
" 2> /dev/null)

$(cat $PSQL_INIT/relationships.sql)

$(cat $PSQL_INIT/views.sql)

$(cat $PSQL_INIT/users-with-meta-triggers.sql)
PSQL
