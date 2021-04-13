#!/bin/bash

cat_sql() {
    cat /docker-entrypoint-initdb.d/src$@.sql
}

psql << SQL
$(cat_sql /settings/*)
$(cat_sql /extensions/*)
$(cat_sql /functions/not-empty)
$(cat_sql /functions/no-leading-or-trailing-spaces)
$(cat_sql /domains/*)
$(cat_sql /functions/generate-random-metakey)
$(cat_sql /functions/sha1-encrypt)
$(cat_sql /functions/truncate)
$(cat_sql /functions/wrap)
$(cat_sql /functions/abbreviate-multiline-text)
$(cat_sql /tables/meta)
$(cat_sql /tables/users)
$(cat_sql /tables/items)
$(cat_sql /indexes/*)
$(cat_sql /views/users-with-meta)
$(cat_sql /views/users-list)
$(cat_sql /views/items-with-meta)
$(cat_sql /views/items-list)
$(cat_sql /functions/*-for-*-with-meta)
$(cat_sql /triggers/*)
SQL
