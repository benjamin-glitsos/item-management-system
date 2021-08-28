FROM node:current-alpine3.10

ENV PATH=/app/node_modules/.bin:$PATH

RUN addgroup -S dgroup \
&& adduser -S duser -G dgroup \
&& mkdir /app/ \
&& mkdir /app/src/ \
&& mkdir /app/src/assets/ \
&& chown duser:dgroup /app/src/assets/ \
&& touch /app/src/assets/README.md \
&& chown duser:dgroup /app/src/assets/README.md \
&& mkdir /app/public/ \
&& chown duser:dgroup /app/public/

WORKDIR /app

COPY . .

RUN npm install \
&&  npm install -g serve

ENTRYPOINT ["sh", "./docker-entrypoint.sh"]
