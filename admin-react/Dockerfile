FROM node:current-alpine3.10
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
COPY . .
RUN npm install
ENTRYPOINT ./docker-entrypoint.sh
