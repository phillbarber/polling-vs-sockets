package com.github.phillbarber.job


import org.slf4j.LoggerFactory
import rx.Observable
import rx.Single
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit


class JobService(val maxDurationInMS: Int = 10000) {



    val logger = LoggerFactory.getLogger(this.javaClass)

    val jobs: ConcurrentHashMap<String, Job> = ConcurrentHashMap()

    fun storeJob(job: Job): Single<Job> {
        jobs.put(job.id, job)
        return makeJobCompleteInRandomTime(job)
    }

    fun getJob(jobId: String): Job? {
        return jobs.get(jobId);
    }

    private fun makeJobCompleteInRandomTime(job: Job) :Single<Job> {

        val currentTimeMillis1 = System.currentTimeMillis()
        val randomDelayNoLongerThanMax = getRandomDelayNoLongerThanMax()
        logger.info("Job: ${job.id} Delay: ${randomDelayNoLongerThanMax}ms")
        val expiryEpochTime: Long = currentTimeMillis1 + randomDelayNoLongerThanMax

        val singleJob = Observable.interval(randomDelayNoLongerThanMax, TimeUnit.MILLISECONDS).doOnNext {
            logger.info("Job ${job.id} is complete")
            job.complete = true
        }.take(1).toSingle().map { job }

        singleJob.subscribe()
        return singleJob;
    }

    private fun getRandomDelayNoLongerThanMax(): Long = Math.round(Math.random() * maxDurationInMS.toLong())

}

