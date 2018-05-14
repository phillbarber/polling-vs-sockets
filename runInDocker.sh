#!/bin/bash
set -e;

USER_NAME="phillbarber"
SHORT_NAME=polling-vs-websockets

FULL_IMAGE_NAME="$USER_NAME/$SHORT_NAME";

docker run --kernel-memory=1024m --cpus=1 -d -p 8080:8080 -p 8081:8081 $FULL_IMAGE_NAME /startServerInDocker.sh