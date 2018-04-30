package com.github.phillbarber.job

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.WebSocketAdapter
import rx.functions.Action1

val jobService = JobService()

val objectMapper = ObjectMapper().registerModule(KotlinModule())


class JobSocket : WebSocketAdapter() {

    override fun onWebSocketConnect(sess: Session?) {
        super.onWebSocketConnect(sess)
        var storeJob = jobService.storeJob(Job())
        storeJob.subscribe(Action1 {
            remote!!.sendString(objectMapper.writeValueAsString(it))
            session.close(200, "Done")
        })
    }
}