FROM openjdk:17-jdk-slim

# Install jq
RUN apt-get update && apt-get install -y jq curl && apt-get clean

WORKDIR /app

# Copy all jars and scripts into the image
COPY target/docker-resources .
COPY ./runner.sh .

# Make runner.sh executable
RUN chmod +x ./runner.sh

# Default command (can be overridden)
ENTRYPOINT ["./runner.sh"]