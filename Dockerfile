# Step 1: Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk

## Step 2: Set the working directory in the container
#WORKDIR /app
#
#RUN mkdir -p /app

# Step 3: Copy the build artifacts from the host to the container
# This assumes your Kotlin Spring Boot app has been built into a JAR file
COPY build/libs/backend-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose the application port (default for Spring Boot is 8080)
EXPOSE 8080

# Step 5: Run the Spring Boot app
# Use exec to allow proper signal forwarding
ENTRYPOINT ["java","-jar","/app.jar"]