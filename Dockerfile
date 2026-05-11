FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/attendance-system-0.0.1-SNAPSHOT.jar attendance-system.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "attendance-system.jar"]