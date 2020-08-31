FROM adoptopenjdk/openjdk11:alpine
RUN apk add --no-cache curl \
&&  apk add --no-cache bash \
&&  apk add --no-cache vim \
&&  curl -Ls https://git.io/sbt > /usr/local/bin/sbt \
&&  chmod 0755 /usr/local/bin/sbt
WORKDIR /usr/src/app
COPY . .
CMD bash
