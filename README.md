# PollingVsSockets

A web application and test setup to compare HTTP Polling and Websockets. 

---

## Build and run

1. Run `mvn clean install` to build application
2. Start application with `java -jar target/polling-vs-sockets-1.0-SNAPSHOT.jar`
3. To create a job via http run: `curl -X POST http://localhost:8080/job`
4. To retrieve a job via http run `curl http://localhost:8080/job/${jobID}`
---

##Build and run in Docker 

1. Run `mvn clean install; ./buildDockerImage.sh`
2. Run `./runInDocker.sh`

##Run Performance tests

1. [Install Jmeter](https://jmeter.apache.org/download_jmeter.cgi)
2. Install [Maciej Zaleski's JMeter-WebSocketSampler](https://github.com/maciejzaleski/JMeter-WebSocketSampler/wiki).  You can also retrieve the plugin's dependencies by running `cd performancetests; mvn dependency:copy-dependencies` which should download them into: polling-vs-sockets/performancetests/target/dependency/sd 
3. Enable docker API via guide [here](https://www.ivankrizsan.se/2016/05/18/enabling-docker-remote-api-on-ubuntu-16-04/)
3. Run container in docker `./runInDocker.sh`
4. From perfomance tests dir `cd performancetests` 
5. `./runPollingPerfTest.sh` OR `./runWebSocketsPerfTest.sh`
6. View the report and other metrics for your test run in the report dir, e.g. `polling-vs-sockets/performancetests/target/report/polling/2018-06-22T22:13:41+01:00/html/index.html`
