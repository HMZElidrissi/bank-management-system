FROM maven:3.9.5-eclipse-temurin-17-alpine AS builder
WORKDIR /app
# Copy the POM file from root directory
COPY ../../pom.xml .
# Download dependencies
RUN mvn dependency:go-offline

# Copy the source code from root directory
COPY ../../src ./src

# Package the application
RUN mvn clean package -DskipTests

# Build the final image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Add a spring user to run the application as a non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy the JAR file to the final image
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

# Configure JVM options
ENV JAVA_OPTS="-Xmx512m -Xms256m"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]