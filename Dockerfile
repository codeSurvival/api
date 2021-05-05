FROM gradle:7.0.0-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11-jre-slim
USER root
WORKDIR app
EXPOSE 8081
ENV SPRING_DATASOURCE_URL="fill it" SPRING_DATASOURCE_USERNAME="fill it" SPRING_DATASOURCE_PASSWORD="fill it"

COPY --from=build /home/gradle/src/web/build/libs/*.jar /app/codesurvival.jar

ENTRYPOINT ["java", "-jar","codesurvival.jar"]
