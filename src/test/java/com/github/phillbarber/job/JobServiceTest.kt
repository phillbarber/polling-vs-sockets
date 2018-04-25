package com.github.phillbarber.job

import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert.*
import org.junit.Test
import rx.Single
import rx.Subscriber
import rx.functions.Action1
import java.util.concurrent.TimeUnit

import org.hamcrest.CoreMatchers.`is` as _is

class JobServiceTest{

    @Test
    fun canSaveAndRetrieve(){
        val jobService = JobService()
        jobService.storeJob(Job(id="1234"))
        var job = jobService.getJob("1234")
        assertThat(job!!.id, _is("1234"))
    }

    @Test
    fun jobCompletesWithinMaxTime(){
        val jobService = JobService(1)
        jobService.storeJob(Job(id="1234"))
        Thread.sleep(2)
        var job = jobService.getJob("1234")
        assertThat(job!!.complete, _is(true))
    }

    @Test
    fun jobDoesNotInstantlyComplete(){
        val jobService = JobService(1000000)
        jobService.storeJob(Job(id="1234"))
        var job = jobService.getJob("1234")
        assertThat(job!!.complete, _is(false))
    }

    @Test
    fun storeJobReturnsObservableThatEmitsACompletedJob(){
        val jobService = JobService()
        var jobObservable = jobService.storeJob(Job(id = "1234"))

        var completedJob :Job = jobObservable.timeout(3000, TimeUnit.MILLISECONDS).toBlocking().value()

        assertThat(completedJob, _is(notNullValue()))
        assertThat(completedJob.complete, _is(true))
    }

    @Test
    fun name() {



        val onSubscribe = Single.OnSubscribe<String>() {
            println("FIRST")
            it.onSuccess("dsdsd")
        }

        Single.create(onSubscribe)

        var single = Single.create(onSubscribe)
        println(single.toBlocking().value())
    }
}