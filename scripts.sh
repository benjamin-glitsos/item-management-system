#!/usr/bin/env bash

if [ -f .env ]
then
    export $(cat .env | sed 's/#.*//g' | xargs)
fi

case "$1" in
    "run" )
        docker-compose run $2
    ;;
    "up" )
        echo "Deleting database volume:"
        docker rm -f -v database
        docker-compose up $2
    ;;
    "down" )
        docker-compose down $2
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
    "repl" | "r" )
        docker-compose exec controller bash sbt
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
