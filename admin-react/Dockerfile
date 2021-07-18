FROM node:current-alpine3.10

ENV PATH=/app/node_modules/.bin:$PATH

RUN useradd admin-react \
&& groupadd admin-react \
&& mkdir /app/src/assets/ \
&& chown admin-react:admin-react /app/src/assets/ \
&& touch /app/src/assets/README.md \
&& chown admin-react:admin-react /app/src/assets/README.md \
&& mkdir /app/public/ \
&& chown admin-react:admin-react /app/public/ \

WORKDIR /app

COPY . .

RUN npm install \
&&  npm install -g serve

ENTRYPOINT ["sh", "./docker-entrypoint.sh"]
