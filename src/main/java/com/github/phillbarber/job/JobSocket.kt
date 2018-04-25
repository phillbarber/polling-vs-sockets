package com.github.phillbarber.job

import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.WebSocketAdapter
import rx.functions.Action1
import java.io.IOException

class JobSocket : WebSocketAdapter() {

    val jobService = JobService()

    override fun onWebSocketConnect(sess: Session?) {
        super.onWebSocketConnect(sess)
        var storeJob = jobService.storeJob(Job())
        storeJob.subscribe( Action1 { remote!!.sendString(it.toString()) })

    }


}