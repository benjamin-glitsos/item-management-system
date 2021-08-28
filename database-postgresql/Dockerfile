FROM postgres:13.0-alpine

RUN addgroup -S dgroup \
&& adduser -S duser -G dgroup \
&& touch /docker-entrypoint-initdb.d/initialise.sh \
&& chown duser:dgroup /docker-entrypoint-initdb.d/initialise.sh \
&& mkdir /docker-entrypoint-initdb.d/src/ \
&& chown duser:dgroup /docker-entrypoint-initdb.d/src
