FROM node:17.5.0-alpine3.14 AS build
WORKDIR /usr/src/app
COPY . .
RUN npm ci
RUN npm run build

FROM nginx:stable-alpine

USER nginx

COPY --from=build /usr/src/app/dist /usr/share/nginx/html
COPY ./nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD [ "nginx", "-g", "daemon off;" ]
