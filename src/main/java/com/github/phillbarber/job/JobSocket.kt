package com.github.phillbarber.job

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.WebSocketAdapter
import org.slf4j.LoggerFactory
import rx.functions.Action1
import java.time.Instant

val jobService = JobService()

val objectMapper = ObjectMapper().registerModule(KotlinModule())


class JobSocket : WebSocketAdapter() {

    val logger = LoggerFactory.getLogger(this.javaClass)

    override fun onWebSocketConnect(sess: Session?) {
        super.onWebSocketConnect(sess)
        var storeJob = jobService.storeJob(Job())
        storeJob.subscribe(Action1 {
            remote!!.sendString(objectMapper.writeValueAsString(it))
            try{
                session.close()
            }
            catch (e: Exception){
                logger.error(e.toString())
            }
            logger.info("Job ${it.id} has been sent to client by socket AND CLOSED. Start to finish: " + (Instant.now().toEpochMilli() - it.createTime ))
        })
    }
}