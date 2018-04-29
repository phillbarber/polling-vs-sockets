#!/bin/bash
set -e;

rm target/log || true
rm -rf target/report || true

#https://stackoverflow.com/questions/48291813/specify-number-of-threads-users-on-command-line-for-jmeter

/usr/share/apache-jmeter-4.0/bin/jmeter -e -o target/report -l target/log -n -Jthreads=99 -JjobsPerThread=2 -JpollingDelayMs=100  -t http-polling.jmx
