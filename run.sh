#!/usr/bin/env bash

docker rm -f -v database-postgresql
docker-compose up $2
