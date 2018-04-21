package com.github.phillbarber.job

import io.dropwizard.testing.junit.DropwizardAppRule
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.StatusCode
import org.eclipse.jetty.websocket.api.WebSocketConnectionListener
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage
import org.eclipse.jetty.websocket.api.annotations.WebSocket
import org.junit.Test

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.glassfish.jersey.server.spi.ContainerProvider
import org.junit.Rule
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Future

class JobSocketAcceptanceTest {

    val appRule: DropwizardAppRule<PollingVsSocketsConfiguration> = DropwizardAppRule(PollingVsSocketsApplication::class.java,
            getFileFromClassPath("config.yml").getAbsolutePath())

    @Rule
    fun getTestRule(): DropwizardAppRule<PollingVsSocketsConfiguration> = appRule


    @Test
    fun stuff() {
        /*
        NOTES
        This page was great...
        https://www.eclipse.org/jetty/documentation/9.3.x/jetty-websocket-client-api.html
        ...however, for some reason the SImpleEchoSocket should (at least for my versions) implement the interface WebSocketConnectionListener which that page didn't explain
        ..implementing that method however didn;t get me the onMessage method??
        ..onFrame???
        ..at this point not quite sure if client is good, so hard to know test is valid.
        ..moving on to the server.
         */

        val client = WebSocketClient();
        val socket = SimpleEchoSocket();
        try {
            client.start();

            val request = ClientUpgradeRequest()
            val uri = URI("ws://localhost:8080/ws/job")
            client.connect(socket, uri, request)
            println("Connecting to : $uri")

            // wait for closed socket connection.
            socket.awaitClose(5, TimeUnit.SECONDS);
        } catch (t: Throwable) {
            t.printStackTrace()
        } finally {
            try {
                client.stop()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @WebSocket(maxTextMessageSize = 64 * 1024)
    class SimpleEchoSocket : WebSocketConnectionListener {
        var closeLatch = CountDownLatch(1)
        @SuppressWarnings("unused")
        var session: Session? = null;

        fun awaitClose(duration: Long, unit: TimeUnit): Boolean {
            return this.closeLatch.await(duration, unit);
        }

        @OnWebSocketMessage
        fun onMessage(msg: String) {
            println("Got msg: $msg");
        }

        override fun onWebSocketError(p0: Throwable?) {
            throw RuntimeException(p0)
        }

        override fun onWebSocketClose(statusCode: Int, reason: String?) {
            println("Connection closed: $statusCode - $reason");
            this.session = null;
            this.closeLatch.countDown(); // trigger latch
        }

        override fun onWebSocketConnect(session: Session) {
            println("Got connect: ${session}")
            this.session = session;
            try {
                var fut: Future<Void>;
                fut = session.getRemote().sendStringByFuture("Hello");
                fut.get(2, TimeUnit.SECONDS); // wait for send to complete.

                fut = session.getRemote().sendStringByFuture("Thanks for the conversation.");
                fut.get(2, TimeUnit.SECONDS); // wait for send to complete.

                session.close(StatusCode.NORMAL, "I'm done");
            } catch (t: Throwable) {
                t.printStackTrace();
            }
        }
    }
}