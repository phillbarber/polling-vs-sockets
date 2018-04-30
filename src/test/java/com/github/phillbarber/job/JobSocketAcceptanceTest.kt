package com.github.phillbarber.job

import io.dropwizard.testing.junit.DropwizardAppRule
import org.eclipse.jetty.websocket.api.StatusCode
import org.eclipse.jetty.websocket.api.WebSocketAdapter
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage
import org.eclipse.jetty.websocket.api.annotations.WebSocket
import org.junit.Test

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.junit.Rule
import rx.Single
import rx.SingleSubscriber
import org.hamcrest.CoreMatchers.`is` as _is

class JobSocketAcceptanceTest {

    val appRule: DropwizardAppRule<PollingVsSocketsConfiguration> = DropwizardAppRule(PollingVsSocketsApplication::class.java,
            getFileFromClassPath("config.yml").getAbsolutePath())

    @Rule
    fun getTestRule(): DropwizardAppRule<PollingVsSocketsConfiguration> = appRule


    @Test
    fun jobCompletes() {


        val request = ClientUpgradeRequest()

        val client = WebSocketClient();
        val socket = JobClientSocket();
        client.start();

        val uri = URI("ws://localhost:8080/ws/job")
        client.connect(socket, uri, request)

        socket.theJob.subscribe()

        var job = socket.theJob.timeout(5, TimeUnit.SECONDS).toBlocking().value();
        var jacksonJaxbJsonProvider = jacksonJaxbJsonProvider()

        println(job)
    }

    @WebSocket(maxTextMessageSize = 64 * 1024)
    class JobClientSocket : WebSocketAdapter() {

        var subscriber: SingleSubscriber<in Job>? = null

        var theJob: Single<Job> = Single.create(Single.OnSubscribe<Job> { subscriber = it; })


        @OnWebSocketMessage
        override fun onWebSocketText(msg: String) {
            subscriber!!.onSuccess( objectMapper().readValue(msg, Job::class.java))
            session.close(StatusCode.NORMAL, "I'm done");
        }

        override fun onWebSocketError(p0: Throwable?) {
            subscriber!!.onError(p0)
        }

        override fun onWebSocketClose(statusCode: Int, reason: String?) {
            println("Connection closed: $statusCode - $reason");
            super.onWebSocketClose(statusCode, reason)
        }


    }
}