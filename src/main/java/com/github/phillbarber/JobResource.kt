package com.github.phillbarber

import java.net.URI
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/job")
class JobResource(val jobService: JobService) {

    @GET
    @Path("/{jobId}")
    fun get(@PathParam("jobId") jobId: String): Response {
        var job: Job? = jobService.getJob(jobId)
        return job?.let { Response.ok(job).build() } ?: Response.status(404).build()
    }

    @POST
    fun create(): Response {
        val job = Job(complete = false)
        jobService.storeJob(job)
        return Response.created(URI("/job/${job.id}")).entity(job).build()
    }

}