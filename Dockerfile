FROM eclipse-temurin:18-alpine

MAINTAINER Luis Brienze <lfbrienze@gmail.com>

# Copy files
WORKDIR /usr/src
COPY . .

# Create config variables
#ENV BOOTAPP_JAVA_OPTS -Xms256m -Xmx512m
ENV SERVER_PORT 8080

# Expose port for health check
EXPOSE $SERVER_PORT

# Update apk, add bash and curl
RUN apk update && apk add bash && apk add curl

# Generate Jar
RUN ./mvnw install

HEALTHCHECK --interval=5s --timeout=3s CMD curl --fail --silent localhost:$SERVER_PORT/actuator/health | grep UP || exit 1

ENTRYPOINT ["java","-Dspring.profiles.active=localstack","-jar","application/target/crypto-robot-analysis-generator.jar","--server.port=${SERVER_PORT}"]

