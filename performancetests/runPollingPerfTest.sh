#!/bin/bash
set -e;

DATE_TIME="$(date --iso-8601=seconds)"
REPORT_DIR="target/report/polling/$DATE_TIME"
REPORT_HTML_DIR="${REPORT_DIR}/html"
export JVM_ARGS="-Xms512m -Xmx512m"

mkdir -p ${REPORT_DIR}
mkdir -p ${REPORT_HTML_DIR}

curl 'http://localhost:4243/containers/polling-vs-sockets/stats?stream=false' > ${REPORT_DIR}/stats-before.json

COMMAND="jmeter -e -o ${REPORT_HTML_DIR} -l ${REPORT_DIR}/log -n -Jthreads=400 -JjobsPerThread=10 -JmaxDurationOfJobToCompleteMS=11000 -JpollingDelayMS=500  -t http-polling.jmx"

echo "${COMMAND}" > ${REPORT_DIR}/command.txt

eval ${COMMAND}

curl 'http://localhost:4243/containers/polling-vs-sockets/stats?stream=false' > ${REPORT_DIR}/stats-after.json
