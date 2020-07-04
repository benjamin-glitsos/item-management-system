#!/usr/bin/env bash

for arg in "$@"
do
    case $arg in
        "run" | "compose" )
            sudo docker-compose up
        ;;
        "prune" )
            sudo docker container prune
        ;;
        "images" )
            sudo docker image ls -a
        ;;
        * )
            cat ./scripts.sh
        ;;
    esac
done
