FROM gradle:7.0.0-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11-jre-slim
RUN apt-get update
RUN apt-get -y install apt-transport-https ca-certificates curl gnupg2 software-properties-common
RUN curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add -
RUN curl -fsSL https://download.docker.com/linux/ubuntu/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
RUN add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/debian $(lsb_release -cs) stable"
RUN echo \
      "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
      $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null
RUN apt-get update || apt-get install docker-ce docker-ce-cli containerd.io -y
RUN apt-get install gradle -y || echo 'a'
USER root
WORKDIR app
EXPOSE 8081
ENV SPRING_DATASOURCE_URL="fill it" SPRING_DATASOURCE_USERNAME="fill it" SPRING_DATASOURCE_PASSWORD="fill it"

RUN mkdir kotlin-compiler

COPY --from=build /home/gradle/src/web/build/libs/*.jar /app/codesurvival.jar
COPY --from=build /home/gradle/src/kotlin-compiler /app/kotlin-compiler
COPY --from=build /home/gradle/src/.env /app/.env

ENTRYPOINT ["java", "-jar","codesurvival.jar"]
