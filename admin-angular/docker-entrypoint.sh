#!/bin/bash

set -e

if [[ "$PROJECT_DEMO_MODE" == "yes" ]]; then
    npm build
else
    npm build:prod
fi

if [[ "$ADMIN_RUN" == "yes" ]]; then
    if [[ "$PROJECT_DEMO_MODE" == "yes" ]]; then
        npm start
    else
        npm start:prod
    fi
fi
