#!/usr/bin/env bash

if [ $# -eq 2 ]; then
    let adminPort=$1
    let controllerPort=$1
else
    echo "What is ADMIN_PORT?"
    read adminPort
    echo "What is CONTROLLER_PORT?"
    read controllerPort
fi

npm --prefix ./admin-react/ run cypress -- \
    --config baseUrl=http://localhost:$adminPort,\
    apiBaseUrl=http://localhost:$controllerPort
