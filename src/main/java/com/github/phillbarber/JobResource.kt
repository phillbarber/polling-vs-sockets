package com.github.phillbarber

import java.net.URI
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/job")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class JobResource {


    @GET
    fun get(): Response{
        return Response.status(404).build()
    }

    @POST
    fun create():Response{
        return Response.created(URI("zzz")).entity(Job(id = "1234", complete = false)).build()
    }

}