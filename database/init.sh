#!/bin/bash

STAFF_DEPARTMENTS=${STAFF_TABLE}_${DEPARTMENTS_TABLE}

eval "cat << EOSQL
$(cat init/custom-functionality.sql | sed 's#\`#\\`#g' | sed 's#\\#\\\\#g' )
$(cat init/tables.sql )
$(cat init/starting-values.sql )
$(cat init/relationships.sql )
EOSQL
" 2> /dev/null
