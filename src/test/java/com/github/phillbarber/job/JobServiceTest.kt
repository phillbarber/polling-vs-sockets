package com.github.phillbarber.job

import org.junit.Assert.*
import org.junit.Test

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
}