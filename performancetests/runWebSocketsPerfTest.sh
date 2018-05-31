#!/bin/bash
set -e;

DATE_TIME="$(date --iso-8601=seconds)"
REPORT_DIR="target/report/sockets/$DATE_TIME"
REPORT_HTML_DIR="${REPORT_DIR}/html"
export JVM_ARGS="-Xms512m -Xmx1024m"

mkdir -p ${REPORT_DIR}
mkdir -p ${REPORT_HTML_DIR}

COMMAND="/usr/share/apache-jmeter-4.0/bin/jmeter -e -o ${REPORT_HTML_DIR} -l ${REPORT_DIR}/log -n -Jthreads=200 -JjobsPerThread=10 -JmaxDurationOfJobToCompleteMS=11000 -t websockets.jmx"

echo "${COMMAND}" > ${REPORT_DIR}/command.txt

echo "${COMMAND}"

eval ${COMMAND}