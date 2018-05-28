#!/bin/bash
set -e;

USER_NAME="phillbarber"
SHORT_NAME=polling-vs-websockets

FULL_IMAGE_NAME="$USER_NAME/$SHORT_NAME";

docker stop polling-vs-sockets || true
docker rm polling-vs-sockets || true

docker run --memory=1024m --cpus=1 --name polling-vs-sockets -d -p 1000:1000 -p 8080:8080 -p 8081:8081 $FULL_IMAGE_NAME /startServerInDocker.sh