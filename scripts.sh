#!/usr/bin/env bash

case "$1" in
    "run" | "compose" )
        docker-compose up -d $2
    ;;
    "images" )
        docker image ls -a
    ;;
    "containers" )
        docker container ls -a
    ;;
    * )
        cat ./scripts.sh
    ;;
esac
