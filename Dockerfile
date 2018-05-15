FROM openjdk:8-jre

ADD docker/jmxremote.access /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/management/jmxremote.access
ADD docker/jmxremote.password /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/management/jmxremote.password

RUN chmod 600 /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/management/jmxremote.access
RUN chmod 600 /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/management/jmxremote.password
ADD docker/startServerInDocker.sh /startServerInDocker.sh
ADD target/polling-vs-sockets-1.0-SNAPSHOT.jar /server.jar