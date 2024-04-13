FROM nginx

ENV SVC_HOTEL_URL=http://localhost:8089

COPY ./dist/goraceloty-front-end/browser /usr/share/nginx/html
COPY ./nginx.conf /etc/nginx/conf.d/default.template

EXPOSE 80

CMD envsubst '\$SVC_HOTEL_URL' < /etc/nginx/conf.d/default.template > /etc/nginx/conf.d/default.conf && exec nginx -g 'daemon off;'
