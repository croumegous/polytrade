FROM mozilla/sbt as build
WORKDIR /usr/src/app

ARG SBT_VERSION=1.6.2
ARG OPENJDK_TAG=17.0.1

COPY . .

RUN sbt compile && sbt package && mv target/scala-*/*.jar ./scala.jar

FROM openjdk:17.0.1-slim
WORKDIR /usr/src/app

COPY --from=build /usr/src/app/scala.jar /usr/src/app/scala.jar

# https://github.com/docker-library/openjdk/issues/234#issuecomment-610658046
RUN addgroup --system --gid 1001 appuser && \
  adduser --system --uid 1001 --group appuser && \
  chown -R appuser:appuser /usr/src/app

USER appuser

CMD [ "java", "-jar", "/usr/src/app/scala.jar" ]