FROM node:current-alpine3.10
WORKDIR /usr/src/app
COPY . .
RUN npm install -g @angular/cli \
&&  npm install 
RUN if [[ "$ADMIN_RUN" == "yes" ]]; \
    then npm start; \
    fi
CMD sh
