#!/usr/bin/env bash

for arg in "$@"
do
    case $arg in
        "compose" )
            sudo docker-compose up
        ;;
        "prune" )
            sudo docker container prune
        ;;
        "images" )
            sudo docker image ls -a
        ;;
        "run postgres" )
            sudo docker run postgres
        ;;
        "run adminer" )
            sudo docker run adminer
        ;;
        "run sbt" )
            sudo docker run -it --rm mozilla/sbt sbt shell
        ;;
        * )
            echo "Open this file in a text editor to view the shell scripts that are commonly used for this project"
        ;;
    esac
done
