FROM gradle:7.6.0-jdk8 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN JAVA_TOOL_OPTIONS=-Dhttps.protocols=TLSv1.2 gradle --stacktrace build --no-daemon
RUN ls -ltr /home/gradle/src/build/libs

FROM openjdk:18-alpine
EXPOSE 8080
RUN apk add zip unzip wget
RUN mkdir /app
RUN wget -O /app/elastic-apm-agent-1.33.0.jar https://search.maven.org/remotecontent?filepath=co/elastic/apm/elastic-apm-agent/1.33.0/elastic-apm-agent-1.33.0.jar
RUN ls -altr /app/
COPY --from=build /home/gradle/src/build/libs/client-0.0.1-SNAPSHOT.jar /app/spring-boot-application.jar
COPY start.sh /app/

ENTRYPOINT ["sh", "/app/start.sh"]