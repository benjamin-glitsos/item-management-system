#!/usr/bin/env bash

for arg in "$@"
do
    case $arg in
        "run" )
            sudo docker-compose up
        ;;
        "prune" )
            sudo docker container prune
        ;;
        * )
            echo "Open this file in a text editor to view the shell scripts that are commonly used for this project"
        ;;
    esac
done
