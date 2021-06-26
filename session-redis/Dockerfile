FROM redis:6.2.4-alpine

ARG REDIS_PORT
ARG REDIS_PASSWORD

CMD "/usr/bin/redis-server --port $REDIS_PORT --requirepass \"$REDIS_PASSWORD\" && /bin/sleep 9999999"
