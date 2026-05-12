FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/*.jar attendance-system.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "attendance-system.jar"]