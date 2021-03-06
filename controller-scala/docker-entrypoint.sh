#!/bin/bash

set -e

if [[ "$CONTROLLER_RUN" == "yes" ]]; then
    cd /usr/src/app
    sbt run
fi
