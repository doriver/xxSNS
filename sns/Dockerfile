FROM eclipse-temurin:17-jdk-alpine-3.23
ARG JAR_FILE=build/libs/*SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
RUN mkdir -p /upload/images
ENTRYPOINT ["java","-jar","/app.jar"]