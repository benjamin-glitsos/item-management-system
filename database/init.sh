#!/bin/bash

# Table Names:

STAFF_DEPARTMENTS=${STAFF_TABLE}_${DEPARTMENTS_TABLE}
STAFF_RECORD_INSERT=${STAFF_TABLE}_${RECORDS_TABLE}_insert
USER_RECORD_INSERT=${USERS_TABLE}_${RECORDS_TABLE}_insert

# Other Values:

SUPER_ADMIN_RECORD_NOTE="Do not edit or delete this record. It belongs to the Super Admin."

# Run SQL:

psql -f /docker-entrypoint-initdb.d/init/domains.sql
psql << EOP
$(eval "cat << EOSQL
$(cat /docker-entrypoint-initdb.d/init/schema.sql)
$(cat /docker-entrypoint-initdb.d/init/starting-data.sql)
$(cat /docker-entrypoint-initdb.d/init/relationships.sql)
EOSQL
" 2> /dev/null)

EOP
