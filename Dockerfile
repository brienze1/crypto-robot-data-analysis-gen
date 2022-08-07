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
RUN ./mvnw install

# Add docker-compose-wait tool - Used to run with localstack docker compose
ENV WAIT_VERSION 2.7.2
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/$WAIT_VERSION/wait /wait
RUN chmod +x /wait

HEALTHCHECK --interval=5s --timeout=3s CMD curl --fail --silent localhost:$SERVER_PORT/actuator/health | grep UP || exit 1

ENTRYPOINT []

