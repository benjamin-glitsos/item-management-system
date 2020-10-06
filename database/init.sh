#!/bin/bash

psql -f /docker-entrypoint-initdb.d/init/domains.sql
psql << EOP
$(eval "cat << EOSQL
$(cat /docker-entrypoint-initdb.d/init/schema.sql)
$(cat /docker-entrypoint-initdb.d/init/data.sql)
$(cat /docker-entrypoint-initdb.d/init/relationships.sql)
EOSQL
" 2> /dev/null)

EOP
