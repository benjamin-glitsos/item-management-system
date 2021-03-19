#!/bin/bash

cat_sql() {
    cat /docker-entrypoint-initdb.d/src$@.sql
}

psql << SQL
$(cat_sql /extensions/*)
$(cat_sql /domains/*)
$(cat_sql /functions/*)
$(cat_sql /tables/meta)
$(cat_sql /tables/users)
$(cat_sql /views/*)
$(cat_sql /triggers/*)
SQL
