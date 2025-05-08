FROM bellsoft/liberica-openjdk-alpine:latest

# Install required software
RUN apk add --no-cache curl jq

# Set working directory inside container
WORKDIR /project-package

# Copy the packaged test framework files
COPY target/docker-resources/ ./

# Copy runner script
COPY ./target/docker-resources ./
COPY runner.sh runner.sh

# Make runner script executable
RUN chmod +x runner.sh

# Set the entry point
ENTRYPOINT ["sh", "runner.sh"]