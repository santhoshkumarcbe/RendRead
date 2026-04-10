# Use lightweight Java image
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# ✅ Correct path for Gradle
COPY build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]