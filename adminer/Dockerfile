FROM adminer:4.7.7-standalone
WORKDIR /var/www/html/
COPY additional.css .
RUN cp /var/www/html/designs/pepa-linha/adminer.css /var/www/html/adminer.css \
&&  cat additional.css >> adminer.css \
&&  rm additional.css
