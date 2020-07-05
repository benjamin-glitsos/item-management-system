FROM node
RUN mkdir /code
WORKDIR /code
COPY . /code
EXPOSE 4200
RUN npm install -g @angular/cli \
    npm install \
    npm run build \
    npm run start
