#!/usr/bin/env bash

for arg in "$@"
do
    case $arg in
        "run" | "compose" )
            docker-compose up
        ;;
        "prune" )
            docker container prune
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
done
