package com.github.phillbarber.job

import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.WebSocketAdapter
import rx.functions.Action1

val jobService = JobService()

class JobSocket : WebSocketAdapter() {

    override fun onWebSocketConnect(sess: Session?) {
        super.onWebSocketConnect(sess)
        var storeJob = jobService.storeJob(Job())
        storeJob.subscribe(Action1 {
            remote!!.sendString(it.toString())
            session.close(200, "Done")
        })
    }
}