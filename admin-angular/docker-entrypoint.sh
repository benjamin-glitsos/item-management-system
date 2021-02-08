#!/bin/bash

set -e

if [[ "$ADMIN_RUN" == "yes" ]]; then
    if [[ "$PROJECT_DEMO_MODE" == "yes" ]]; then
        npm start
    else
        npm build:prod && npm start:prod
    fi
fi
