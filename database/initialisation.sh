#!/bin/bash

cat_sql() {
    local filename=${1}
    cat "/docker-entrypoint-initdb.d/initialisation/$filename.sql"
}

cat_interpolated_sql() {
    local filename=${1}
    eval "echo \"$(cat_sql "$filename")\""
}

psql << SQL

$(cat_sql "extensions")

$(cat_sql "domains")

$(cat_sql "tables")

$(cat_sql "sha1-encrypt")

$(cat_sql "relationships")

$(cat_sql "views")

$(cat_sql "users-with-meta-trigger")

SQL
