FROM openjdk:8-jre

ADD docker/startServerInDocker.sh /startServerInDocker.sh
ADD target/polling-vs-sockets-1.0-SNAPSHOT.jar /server.jar