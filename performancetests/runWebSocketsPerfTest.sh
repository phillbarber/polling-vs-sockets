#!/bin/bash
set -e;

DATE_TIME="$(date --iso-8601=seconds)"
REPORT_DIR="target/report/sockets/$DATE_TIME/A"
REPORT_DIR2="target/report/sockets/$DATE_TIME/B"
REPORT_HTML_DIR="${REPORT_DIR}/html"
REPORT_HTML_DIR2="${REPORT_DIR2}/html"
export JVM_ARGS="-Xms512m -Xmx1024m"

mkdir -p ${REPORT_DIR}
mkdir -p ${REPORT_DIR2}
mkdir -p ${REPORT_HTML_DIR}
mkdir -p ${REPORT_HTML_DIR2}

curl 'http://localhost:4243/containers/polling-vs-sockets/stats?stream=false' > ${REPORT_DIR}/stats-before.json

COMMAND="jmeter -e -o ${REPORT_HTML_DIR} -l ${REPORT_DIR}/log -n -Jthreads=200 -JjobsPerThread=10 -JmaxDurationOfJobToCompleteMS=11000 -t websockets.jmx"
COMMAND2="jmeter -e -o ${REPORT_HTML_DIR2} -l ${REPORT_DIR2}/log -n -Jthreads=200 -JjobsPerThread=10 -JmaxDurationOfJobToCompleteMS=11000 -t websockets.jmx"

echo "${COMMAND}" > ${REPORT_DIR}/command.txt
echo "${COMMAND}" > ${REPORT_DIR2}/command.txt

echo "${COMMAND}"

eval ${COMMAND} & eval ${COMMAND2}

curl 'http://localhost:4243/containers/polling-vs-sockets/stats?stream=false' > ${REPORT_DIR}/stats-after.json
