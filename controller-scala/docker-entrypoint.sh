#!/bin/bash

set -e

if [[ "$1" == "yes" ]]; then
    sbt run
fi
