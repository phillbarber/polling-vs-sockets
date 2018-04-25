package com.github.phillbarber.job

import io.dropwizard.testing.junit.DropwizardAppRule
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.StatusCode
import org.eclipse.jetty.websocket.api.WebSocketAdapter
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage
import org.eclipse.jetty.websocket.api.annotations.WebSocket
import org.junit.Test

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsIterableContainingInOrder.contains
import org.junit.Rule
import rx.Single
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Future
import org.hamcrest.CoreMatchers.`is` as _is

class EchoSocketAcceptanceTest {

    val appRule: DropwizardAppRule<PollingVsSocketsConfiguration> = DropwizardAppRule(PollingVsSocketsApplication::class.java,
            getFileFromClassPath("config.yml").getAbsolutePath())

    @Rule
    fun getTestRule(): DropwizardAppRule<PollingVsSocketsConfiguration> = appRule


    @Test
    fun stuff()  {
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
        val socket = SimpleMessageSocket();
        client.start();

        val request = ClientUpgradeRequest()
        val uri = URI("ws://localhost:8080/echo")
        client.connect(socket, uri, request)
        println("Connecting to : $uri")

        // wait for closed socket connection.
        socket.awaitClose(5, TimeUnit.SECONDS);

        assertThat(socket.messagesReceived, contains("Thanks for saying Hello"))
    }

    @WebSocket(maxTextMessageSize = 64 * 1024)
    class SimpleMessageSocket : WebSocketAdapter() {
        var closeLatch = CountDownLatch(1)


        var messagesReceived = ArrayList<String>()

        fun awaitClose(duration: Long, unit: TimeUnit): Boolean {
            return this.closeLatch.await(duration, unit);
        }

        @OnWebSocketMessage
        override fun onWebSocketText(msg: String) {
            messagesReceived.add(msg)
            println("Client here, just got msg: $msg");
        }

        override fun onWebSocketError(p0: Throwable?) {
            throw RuntimeException(p0)
        }

        override fun onWebSocketClose(statusCode: Int, reason: String?) {
            println("Connection closed: $statusCode - $reason");
            super.onWebSocketClose(statusCode, reason)
            this.closeLatch.countDown(); // trigger latch
        }

        override fun onWebSocketConnect(session: Session) {
            super.onWebSocketConnect(session)

            println("Got connect: ${session}")

            try {
                var fut: Future<Void>;
                fut = session.getRemote().sendStringByFuture("Hello");
                fut.get(2, TimeUnit.SECONDS); // wait for send to complete.

                session.close(StatusCode.NORMAL, "I'm done");
            } catch (t: Throwable) {
                t.printStackTrace();
            }
        }
    }
}