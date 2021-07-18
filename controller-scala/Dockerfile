FROM adoptopenjdk/openjdk11:alpine

RUN apk add --no-cache curl bash \
&&  curl -Ls https://git.io/sbt > /usr/local/bin/sbt \
&&  chmod 0755 /usr/local/bin/sbt

RUN useradd controller-scala \
&& groupadd controller-scala \
&& mkdir /usr/src/app/public/ \
&& chown controller-scala:controller-scala /usr/src/app/public/

WORKDIR /usr/src/app

COPY . .

RUN sbt compile

ENTRYPOINT ./docker-entrypoint.sh
