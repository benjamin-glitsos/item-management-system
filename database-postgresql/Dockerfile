FROM postgres:13.0-alpine

RUN useradd database-postgresql
&& groupadd database-postgresql
&& touch /docker-entrypoint-initdb.d/initialise.sh \
&& chown database-postgresql:database-postgresql /docker-entrypoint-initdb.d/initialise.sh \
&& mkdir /docker-entrypoint-initdb.d/src/ \
&& chown chown database-postgresql:database-postgresql /docker-entrypoint-initdb.d/src
