#!/usr/bin/env bash

for arg in "$@"
do
    case $arg in
        "run" )
            sudo docker-compose up
        ;;
        "run postgres" )
            docker run postgres
        ;;
        "run adminer" )
            docker run adminer
        ;;
        "run -it --rm mozilla/sbt" )
            docker run mozilla/sbt
        ;;
        "prune" )
            sudo docker container prune
        ;;
        * )
            echo "Open this file in a text editor to view the shell scripts that are commonly used for this project"
        ;;
    esac
done
