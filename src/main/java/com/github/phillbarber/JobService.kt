package com.github.phillbarber

class JobService{

    val jobs: MutableMap<String,Job> = HashMap()

    fun storeJob(job: Job){
        jobs.put(job.id, job);
    }

    fun getJob(jobId: String): Job?{
        return jobs.get(jobId);
    }

}