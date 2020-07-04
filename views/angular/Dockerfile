FROM node
RUN mkdir ~/code
WORKDIR ~/code
COPY . .
RUN npm install -g @angular/cli
RUN npm install
RUN npm run build
RUN npm run serve
