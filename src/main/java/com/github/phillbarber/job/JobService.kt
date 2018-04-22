package com.github.phillbarber.job

import rx.Observable
import java.util.concurrent.TimeUnit

class JobService(val maxDuration: Int = 2000) {

    val jobs: MutableMap<String, Job> = HashMap()

    fun storeJob(job: Job) {
        jobs.put(job.id, job)
        makeJobCompleteInRandomTime(job)
    }

    fun getJob(jobId: String): Job? {
        return jobs.get(jobId);
    }

    private fun makeJobCompleteInRandomTime(job: Job) {

        val currentTimeMillis1 = System.currentTimeMillis()
        val randomDelayNoLongerThanMax = getRandomDelayNoLongerThanMax()
        println("Will wait for $randomDelayNoLongerThanMax")
        val expiryEpochTime: Long = currentTimeMillis1 + randomDelayNoLongerThanMax

        Observable.interval(randomDelayNoLongerThanMax, TimeUnit.MILLISECONDS).doOnNext {
            job.complete = true

        }.take(1).subscribe()
    }

    private fun getRandomDelayNoLongerThanMax(): Long = Math.round(Math.random() * maxDuration.toLong())

}

