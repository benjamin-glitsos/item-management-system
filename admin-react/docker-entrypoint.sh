#!/bin/bash

set -e

export REACT_APP_PROJECT_NAME="$PROJECT_NAME"
export REACT_APP_PROJECT_ABBREV="$PROJECT_ABBREV"
export REACT_APP_PROJECT_MODE="$PROJECT_MODE"

if [[ "$ADMIN_RUN" == "yes" ]]; then
    cd /app
    case "$PROJECT_MODE" in
        "development" )
            FORCE_COLOR=true
            npm start | cat
        ;;
        "production" )
            npm build
        ;;
        * )
            echo "Project mode is not recognised."
        ;;
    esac
fi
