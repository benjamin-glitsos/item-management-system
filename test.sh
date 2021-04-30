#!/usr/bin/env bash

echo "What is ADMIN_PORT?"

read frontEndPort

npm --prefix ./admin-react/ run cypress -- --config baseUrl=http://localhost:$frontEndPort
