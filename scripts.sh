#!/usr/bin/env bash

case "$1" in
    "run" )
        docker-compose run $2
    ;;
    "exec" )
        docker-compose exec $2
    ;;
    "up" )
        docker-compose up -d $2
    ;;
    "stop" )
        docker-compose stop
    ;;
    "images" )
        docker image ls -a
    ;;
    "containers" )
        docker container ls -a
    ;;
    "volumes" )
        docker volume ls
    ;;
    * )
        cat ./scripts.sh
    ;;
esac
