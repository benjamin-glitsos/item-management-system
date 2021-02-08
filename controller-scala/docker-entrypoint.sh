#!/bin/bash

set -e

if [[ "$CONTROLLER_RUN" == "yes" ]]; then
    sbt run
fi
