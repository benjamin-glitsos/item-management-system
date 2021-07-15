FROM redis:6.2.4-alpine

ARG REDIS_PASSWORD

COPY ./redis.conf /usr/local/etc/redis/redis.conf

CMD redis-server /usr/local/etc/redis/redis.conf
