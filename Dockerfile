FROM adoptopenjdk:11-jre-hotspot AS build

LABEL maintainer="Stoom"
LABEL version="1.0"

EXPOSE 8080

COPY application/target/stoom-qualification*.jar application.jar

ENTRYPOINT [ "java" , "-Xms128m",  "-Xmx400m", "-jar", "-DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector", "application.jar"]