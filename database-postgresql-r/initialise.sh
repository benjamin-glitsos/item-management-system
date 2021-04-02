#!/bin/bash

cat_sql() {
    cat /docker-entrypoint-initdb.d/src$@.sql
}

psql << SQL
$(cat_sql /extensions/*)
$(cat_sql /domains/*)
$(cat_sql /functions/generate-random-metakey)
$(cat_sql /functions/sha1-encrypt)
$(cat_sql /functions/friendly-name)
$(cat_sql /tables/meta)
$(cat_sql /tables/users)
$(cat_sql /views/users-with-meta)
$(cat_sql /views/users-list)
$(cat_sql /functions/*-for-users-with-meta)
$(cat_sql /triggers/*)
SQL
