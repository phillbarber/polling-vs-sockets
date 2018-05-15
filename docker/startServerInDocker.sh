#!/bin/bash
set -e;


time java -Dcom.sun.management.jmxremote \
            -Dcom.sun.management.jmxremote.port=1000 \
            -Dcom.sun.management.jmxremote.local.only=false \
            -Dcom.sun.management.jmxremote.authenticate=false \
            -Dcom.sun.management.jmxremote.ssl=false \
            -jar /server.jar