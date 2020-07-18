#!/usr/bin/env bash

case "$@" in
    "run" | "build" | "compose" )
        docker-compose up
    ;;
    "images" )
        docker image ls -a
    ;;
    "containers" )
        docker container ls -a
    ;;
    "build app" )
        docker build -t hospital_app ./app/
    ;;
    "run app" )
        docker run \
            -it \
            --volume=app:/root \
            --volume=$HOME/Documents/code/hospital-ms/app:/usr/src/app \
            --env-file .env \
            --expose 3100 \
            hospital_app
    ;;
    "build admin" )
        docker build -t hospital_admin ./admin/
    ;;
    "run admin" )
        docker run -it hospital_admin
    ;;
    "build database" )
        docker build -t hospital_database ./database/
    ;;
    "run database" )
        docker run -it hospital_database
    ;;
    "build database_admin" )
        docker build -t hospital_database_admin ./database-admin/
    ;;
    "run database_admin" )
        docker run -it hospital_database_admin
    ;;
    * )
        cat ./scripts.sh
    ;;
esac
