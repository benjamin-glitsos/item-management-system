FROM redis:6.2.4-alpine

ARG SESSION_PASSWORD

COPY ./redis.conf /usr/local/etc/redis/redis.conf

CMD redis-server /usr/local/etc/redis/redis.conf --requirepass "$SESSION_PASSWORD"
