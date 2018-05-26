# PollingVsSockets

How to startsummary =   1453 in 00:01:34 =   15.4/s Avg:  4785 Min:    21 Max:  9971 Err:  1123 (77.29%)
            summary +    324 in 00:00:10 =   32.2/s Avg:  4881 Min:   134 Max: 10003 Err:   251 (77.47%) Active: 174 Started: 174 Finished: 0
            summary =   1777 in 00:01:44 =   17.0/s Avg:  4803 Min:    21 Max: 10003 Err:  1374 (77.32%)
            summary +    369 in 00:00:10 =   37.1/s Avg:  4927 Min:    38 Max:  9964 Err:   286 (77.51%) Active: 191 Started: 191 Finished: 0
            summary =   2146 in 00:01:54 =   18.8/s Avg:  4824 Min:    21 Max: 10003 Err:  1660 (77.35%)
            summary +    395 in 00:00:10 =   39.4/s Avg:  4819 Min:    53 Max:  9994 Err:   302 (76.46%) Active: 200 Started: 200 Finished: 0
            summary =   2541 in 00:02:04 =   20.4/s Avg:  4823 Min:    21 Max: 10003 Err:  1962 (77.21%)
            summary +    398 in 00:00:10 =   39.9/s Avg:  5120 Min:    28 Max: 10003 Err:   315 (79.15%) Active: 200 Started: 200 Finished: 0
            summary =   2939 in 00:02:14 =   21.9/s Avg:  4863 Min:    21 Max: 10003 Err:  2277 (77.48%)
            summary +    358 in 00:00:10 =   35.8/s Avg:  4889 Min:    10 Max: 10155 Err:   273 (76.26%) Active: 200 Started: 200 Finished: 0
            summary =   3297 in 00:02:24 =   22.9/s Avg:  4866 Min:    10 Max: 10155 Err:  2550 (77.34%)
            summary +    102 in 00:00:11 =    9.3/s Avg:  8371 Min:  2230 Max: 15530 Err:   102 (100.00%) Active: 200 Started: 200 Finished: 0
            summary =   3399 in 00:02:35 =   21.9/s Avg:  4971 Min:    10 Max: 15530 Err:  2652 (78.02%)
            java.lang.OutOfMemoryError: Java heap space
            Dumping heap to java_pid8432.hprof ...

            Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "Thread Group 1-8"
            Heap dump file created [1111626174 bytes in 26.776 secs]

            Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "WebSocketClient@1631894060-scheduler"

            Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "WebSocketClient@1187813081-scheduler"
            summary +     28 in 00:00:30 =    0.9/s Avg: 14373 Min: 11454 Max: 18485 Err:    28 (100.00%) Active: 195 Started: 200 Finished: 5
            summary =   3427 in 00:03:05 =   18.5/s Avg:  5048 Min:    10 Max: 18485 Err:  2680 (78.20%)
            summary +      1 in 00:00:13 =    0.1/s Avg: 57272 Min: 57272 Max: 57272 Err:     1 (100.00%) Active: 194 Started: 200 Finished: 6
            summary =   3428 in 00:03:18 =   17.3/s Avg:  5063 Min:    10 Max: 57272 Err:  2681 (78.21%)

            Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "WebSocketClient@1781945198-35077"

            Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "WebSocketClient@802782031-34852"

            Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "WebSocketClient@845308334-35441"

            Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "WebSocketClient@1681556338-35203"

            Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "WebSocketClient@1122372272-35498"

            Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "WebSocketClient@1153206615-35048"

            Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "WebSocketClient@1110242380-35365"

            Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "WebSocketClient@462391109-35279"
 the PollingVsSockets application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/polling-vs-sockets-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`


cd performancetests
mvn dependency:copy-dependencies


Thanks to https://github.com/maciejzaleski/JMeter-WebSocketSampler
but no error handling... so could use this...
https://www.blazemeter.com/blog/jmeter-websocket-samplers-a-practical-guide



Notes... socket test started failing with out of memory error...

