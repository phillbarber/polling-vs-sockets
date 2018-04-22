package com.github.phillbarber.job

import javax.servlet.annotation.WebServlet
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;


@WebServlet(name = "MyEcho WebSocket Servlet", urlPatterns = arrayOf("/echo"))
class MyEchoServlet : WebSocketServlet() {
    override fun configure(factory: WebSocketServletFactory) {
        // set a 10 second timeout
        factory.getPolicy().setIdleTimeout(10000)

        // register PrintingSocketThatSaysThanks as the WebSocket to create on Upgrade
        factory.register(PrintingSocketThatSaysThanks::class.java)
    }




}