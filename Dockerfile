FROM openjdk:17
WORKDIR /app
COPY target/firstjobapp-0.0.1-SNAPSHOT.jar /app/firstjobapp-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "firstjobapp-0.0.1-SNAPSHOT.jar"]
