#!/bin/sh
JAVA_OPTS="$JAVA_MEMORY -Delastic.apm.service_name=$SERVICE_NAME -Delastic.apm.application_packages=com.robison.apmExample -Delastic.apm.server_url=http://$APM_SERVER:8200"
echo "OPTS: $JAVA_OPTS"
echo "MONGO_URL: $MONGO_URL"
java -javaagent:/app/elastic-apm-agent-1.33.0.jar $JAVA_OPTS -jar /app/spring-boot-application.jar
