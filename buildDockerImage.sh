#!/bin/bash
set -e;

USER_NAME="phillbarber"
SHORT_NAME=polling-vs-websockets

FULL_IMAGE_NAME="$USER_NAME/$SHORT_NAME";

echo "Building with tag: " $FULL_IMAGE_NAME
docker build -t "$FULL_IMAGE_NAME" .;