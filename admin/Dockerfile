## Build --------------------

FROM node:14.5.0-alpine3.10 AS build
WORKDIR /usr/src/app
COPY . .
RUN npm install \
    npm run build

## Run --------------------

FROM nginx:1.19.0-alpine
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /usr/src/app/dist /usr/share/nginx/html
