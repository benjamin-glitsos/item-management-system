#!/usr/bin/env bash

echo "What is ADMIN_PORT? (This is a variable in the env file. It is the port that the front-end admin panel is using.)"

read frontEndPort

npm --prefix ./admin-react/ run cypress -- --config baseUrl=http://localhost:$frontEndPort
