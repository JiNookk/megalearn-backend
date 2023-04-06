FROM openjdk:17

ARG JAR_FILE=build/libs/*[^plain].jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=production", "-jar", "./app.jar"]
