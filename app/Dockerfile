FROM openjdk
RUN curl -Ls https://git.io/sbt > /bin/sbt && chmod 0755 /bin/sbt
RUN mkdir ~/code
WORKDIR ~/code
COPY . ~/code
