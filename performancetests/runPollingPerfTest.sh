#!/bin/bash
set -e;

rm target/log || true
rm -rf target/report || true

/usr/share/apache-jmeter-4.0/bin/jmeter -e -o target/report -l target/log -n -Jthreads=100 -JjobsPerThread=10 -JmaxDurationOfJobToComplete=100 -JpollingDelayMs=100  -t http-polling.jmx
