# Dockerfile3.test-runner

FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy all jars and scripts into the image
COPY target/docker-resources ./project-package
COPY runner.sh ./project-package

# Make runner.sh executable
RUN chmod +x ./project-package/runner.sh

# Default command (can be overridden)
ENTRYPOINT ["./project-package/runner.sh"]