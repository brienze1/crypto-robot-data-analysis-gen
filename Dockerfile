FROM eclipse-temurin:18-alpine

MAINTAINER Luis Brienze <lfbrienze@gmail.com>

# Copy files
WORKDIR /usr/src
COPY . .

# Set server port for healthcheck
ENV SERVER_PORT 8080

# Update apk, add bash and curl
RUN apk update && apk add bash && apk add curl

# Generate Jar
RUN ./mvnw install -B -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn

HEALTHCHECK --interval=5s --timeout=3s CMD curl --fail --silent localhost:$SERVER_PORT/actuator/health | grep UP || exit 1

ENTRYPOINT []

