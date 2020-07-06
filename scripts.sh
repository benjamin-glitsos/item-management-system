#!/usr/bin/env bash

for arg in "$@"
do
    case $arg in
        "run" | "compose" | "build" )
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
        "build app" )
            docker build -t hospital_app ./app/
        ;;
        "run app" )
            docker run -it hospital_app
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
done
