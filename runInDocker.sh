#####################################################
# docker-compose would be better for this but sadly
# version 2 of docker-compose (what I'm limited to)
# does not support resource limiting, so bash it is.
#####################################################
#!/bin/bash
set -e;

USER_NAME="phillbarber"
SHORT_NAME=polling-vs-websockets

FULL_IMAGE_NAME="$USER_NAME/$SHORT_NAME";

docker stop polling-vs-sockets || true
docker rm polling-vs-sockets || true

docker run --memory=1024m --cpuset-cpus=0 --name polling-vs-sockets -d -p 8090:8080 -p 8081:8081 $FULL_IMAGE_NAME /startServerInDocker.sh