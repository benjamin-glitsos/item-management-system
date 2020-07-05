## Build --------------------
FROM openjdk:15-jdk-alpine3.11
ADD https://git.io/sbt /usr/local/bin/sbt
RUN chmod 0755 /usr/local/bin/sbt
WORKDIR /usr/src/app
COPY . .

## Run --------------------

CMD shell sbt
