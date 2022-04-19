FROM hseeberger/scala-sbt:17.0.2_1.6.2_3.1.1 as build
WORKDIR /usr/src/app

ARG SBT_VERSION=1.6.2
ARG OPENJDK_TAG=17.0.1

COPY . .

CMD ["sbt", "run"]