#!/bin/bash

set -e

if [[ "$CONTROLLER_RUN" == "yes" ]]; then
    cd /app
    sbt run
else
    echo "Controller is disabled."
fi
