# Stage 1: Build the application
FROM amazoncorretto:17.0.9-al2023-headful AS build

RUN yum -y install findutils

WORKDIR /app

# Copy the source code into the Docker image
COPY . /app

# Build frontend files so they're available in the build step
RUN ./gradlew install && ./gradlew build

# Make port 8080 available to the world outside this container
EXPOSE 9090

ENV MYSQL_HOST=localhost \
    MYSQL_PORT=3306 \
    MYSQL_DATABASE=invstore \
    MYSQL_USERNAME=root \
    MYSQL_PASSWORD=password

ENTRYPOINT ["java","-jar","/app/build/libs/invstore-jvm-0.0.1-SNAPSHOT.jar"]