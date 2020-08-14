#!/bin/bash

STAFF_DEPARTMENTS=${STAFF_TABLE}_${DEPARTMENTS_TABLE}

psql "$(cat custom-functionality.sql tables.sql starting-values.sql relationships.sql)"
