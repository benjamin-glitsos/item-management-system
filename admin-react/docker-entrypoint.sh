#!/bin/bash

set -e

if [[ "$ADMIN_RUN" == "yes" ]]; then
    cd /app
    case "$PROJECT_MODE" in
        "development" )
            npm start
        ;;
        "production" )
            npm build
        ;;
    esac
fi
