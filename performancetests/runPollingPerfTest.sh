#!/bin/bash
set -e;

DATE_TIME="$(date --iso-8601=seconds)"
REPORT_DIR="target/report/polling/$DATE_TIME"
REPORT_HTML_DIR="${REPORT_DIR}/html"

mkdir -p ${REPORT_DIR}
mkdir -p ${REPORT_HTML_DIR}

COMMAND="/usr/share/apache-jmeter-4.0/bin/jmeter -e -o ${REPORT_HTML_DIR} -l ${REPORT_DIR}/log -n -Jthreads=10 -JjobsPerThread=3 -JmaxDurationOfJobToComplete=10500 -JpollingDelayMs=500  -t http-polling.jmx"

echo "${COMMAND}" > ${REPORT_DIR}/command.txt

echo "${COMMAND}"

eval ${COMMAND}
