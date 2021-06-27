FROM redis:6.2.4-alpine

ARG REDIS_PORT
ARG REDIS_PASSWORD

CMD redis-server --port $REDIS_PORT --requirepass "$REDIS_PASSWORD"
