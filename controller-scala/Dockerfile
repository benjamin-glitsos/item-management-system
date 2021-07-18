FROM adoptopenjdk/openjdk11:alpine

RUN apk add --no-cache curl bash \
&&  curl -Ls https://git.io/sbt > /usr/local/bin/sbt \
&&  chmod 0755 /usr/local/bin/sbt

RUN addgroup -S dgroup \
&& adduser -S duser -G dgroup \
&& mkdir /app/ \
&& mkdir /app/public/ \
&& chown duser:dgroup /app/public/

WORKDIR /app

COPY . .

RUN sbt compile

ENTRYPOINT ./docker-entrypoint.sh
