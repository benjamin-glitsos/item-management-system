#!/usr/bin/env bash

if [ $# -gt 0 ]; then
    let frontEndPort=$*
else
    echo "What is ADMIN_PORT?"
    read frontEndPort
fi

npm --prefix ./admin-react/ run cypress -- \
    --config baseUrl=http://localhost:$frontEndPort
