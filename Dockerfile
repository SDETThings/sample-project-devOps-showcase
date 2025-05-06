FROM bellsoft/liberica-openjdk-alpine:latest

#Install softwares
RUN apk add curl jq

#set work directory inside container
WORKDIR /03-automation-framework/selenium-docker

#Add the required files
ADD /target/docker-resources    ./
ADD runner.sh                   runner.sh

#Run the tests
ENTRYPOINT sh runner.sh

