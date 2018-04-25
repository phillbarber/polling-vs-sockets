package com.github.phillbarber.job

import org.eclipse.jetty.websocket.api.WebSocketAdapter
import java.io.IOException

class PrintingSocketThatSaysThanks: WebSocketAdapter() {

/*
WARN  [2018-04-22 20:04:54,086] org.eclipse.jetty.server.HttpChannel: /echo
! org.eclipse.jetty.websocket.api.InvalidWebSocketException: com.github.phillbarber.job.PrintingSocketThatSaysThanks is not a valid WebSocket object.  Object must obey one of the following rules:
! (1) class implements org.eclipse.jetty.websocket.api.WebSocketListener or
! (2) class is annotated with @org.eclipse.jetty.websocket.api.annotations.WebSocket




 */


    override fun onWebSocketText(message: String) {
        if (session == null) {
            // no connection, do nothing.
            // this is possible due to async behavior
            return
        }

        try {
            remote!!.sendString("Thanks for saying $message")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}