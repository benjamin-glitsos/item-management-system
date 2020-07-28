#!/usr/bin/env bash

case "$1" in
    "run" )
        docker-compose run $2
    ;;
    "up" )
        docker-compose up $2
    ;;
    "bg" )
        docker-compose up -d $2
    ;;
    "build" )
        docker-compose build $2
    ;;
    "stop" )
        docker-compose stop
    ;;
    "exec" )
        docker-compose exec $2
    ;;
    "bash" )
        docker-compose exec $2 bash
    ;;
    "inspect" )
        docker inspect $2 | less
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
