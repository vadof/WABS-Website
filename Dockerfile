FROM openjdk:17

WORKDIR /app

COPY target/website-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/static/ /app/static/

EXPOSE 8080

CMD [ "java", "-jar", "app.jar" ]