#!/bin/bash

cat_sql() {
    local filename=${1}
    cat /docker-entrypoint-initdb.d/initialisation/$filename.sql
}

psql << PSQL
$(cat_sql "domains")

$(cat_sql "tables")

$(cat_sql "functions")

$(eval "cat << PRE_INTERPOLATED_PSQL
$(cat_sql "data")
PRE_INTERPOLATED_PSQL
" 2> /dev/null)

$(cat_sql "relationships")

$(cat_sql "views")

$(cat_sql "users-with-meta-triggers")
PSQL
