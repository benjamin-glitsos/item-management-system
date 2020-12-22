#!/bin/bash

psql -f /docker-entrypoint-initdb.d/init/domains.sql
psql -f /docker-entrypoint-initdb.d/init/tables.sql
psql << EOP
$(eval "cat << EOSQL
$(cat /docker-entrypoint-initdb.d/init/data.sql)
EOSQL
" 2> /dev/null)
EOP
psql -f /docker-entrypoint-initdb.d/init/relationships.sql
psql -f /docker-entrypoint-initdb.d/init/views.sql
psql -f /docker-entrypoint-initdb.d/init/triggers.sql

# TODO: tidy this code. Reduce redundancy.
