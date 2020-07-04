FROM node
RUN mkdir ~/code
WORKDIR ~/code
COPY . .
RUN npm install
RUN npm run build
RUN npm run start
