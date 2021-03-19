#!/bin/bash

cat_sql() {
    local filepath=${1}
    cat "/docker-entrypoint-initdb.d/src/$filepath.sql"
}

psql << SQL
$(cat_sql "extensions/*")
$(cat_sql "domains/*")
$(cat_sql "functions/*")
$(cat_sql "tables/meta")
$(cat_sql "tables/users")
$(cat_sql "views/*")
$(cat_sql "triggers/*")
SQL
