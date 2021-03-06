FROM node:current-alpine3.10
ENV PATH=/app/node_modules/.bin:$PATH
WORKDIR /app
COPY . .
RUN npm install
ENTRYPOINT ["sh", "./docker-entrypoint.sh"]
