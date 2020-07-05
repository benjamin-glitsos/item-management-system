FROM openjdk
RUN curl -Ls https://git.io/sbt > /bin/sbt \
    chmod 0755 /bin/sbt \
    mkdir /code
WORKDIR /code
COPY . /code
