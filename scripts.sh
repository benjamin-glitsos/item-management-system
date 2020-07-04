#!/usr/bin/env bash

for arg in "$@"
do
    case $arg in
        "run" )
            sudo docker-compose up
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
        "prune" )
            sudo docker container prune
        ;;
        * )
            echo "Open this file in a text editor to view the shell scripts that are commonly used for this project"
        ;;
    esac
done
