package com.github.phillbarber.job

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.dropwizard.testing.junit.DropwizardAppRule
import org.glassfish.jersey.client.ClientConfig
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.MediaType
import org.hamcrest.CoreMatchers.`is` as _is


fun jacksonJaxbJsonProvider(): JacksonJaxbJsonProvider {
    val jacksonProvider = JacksonJaxbJsonProvider()
    val mapper = objectMapper()
    jacksonProvider.setMapper(mapper)
    return jacksonProvider
}

fun objectMapper(): ObjectMapper {
    return ObjectMapper().registerModule(KotlinModule())
}

class JobHTTPAcceptanceTest {

    val appRule: DropwizardAppRule<PollingVsSocketsConfiguration> = DropwizardAppRule(PollingVsSocketsApplication::class.java,
            getFileFromClassPath("config.yml").getAbsolutePath())

    @Rule
    fun getTestRule(): DropwizardAppRule<PollingVsSocketsConfiguration> = appRule

    val client = createClient()
    val url = "http://localhost:8080/job"


    @Test
    fun getJobReturns404WhenNonExistent(){
        var get = client.target(url+"/blah")
                .request(MediaType.APPLICATION_JSON)
                .get()
        assertThat(get.status, _is(404))
    }

    @Test
    fun createJobReturnsNonCompleteJob() {
        var response = client.target(url).request(MediaType.APPLICATION_JSON).post(null)
        var entity = response.readEntity(Job::class.java)
        assertThat(response.status, _is(201))
        assertThat(entity.id, _is(notNullValue()))
        assertThat(entity.complete, _is(false))
    }


    @Test
    fun createdJobCompletesNoLongerThanMaxDuration() {
        var postResponse = client.target(url).request(MediaType.APPLICATION_JSON).post(null)

        val headerString = postResponse.getHeaderString("Location")
        val MAX_DURATION: Long = 10000
        Thread.sleep(MAX_DURATION)
        var getResponse = client.target(headerString).request(MediaType.APPLICATION_JSON).get()
        var retrievedJob = getResponse.readEntity(Job::class.java)

        assertThat(retrievedJob.complete, equalTo(true))
    }

    @Test
    fun createdJobCanBeRetrieved(){
        var postResponse = client.target(url).request(MediaType.APPLICATION_JSON).post(null)
        var createdJob = postResponse.readEntity(Job::class.java)


        val headerString = postResponse.getHeaderString("Location")
        var getResponse = client.target(headerString).request(MediaType.APPLICATION_JSON).get()
        var retrievedJob = getResponse.readEntity(Job::class.java)

        assertThat(retrievedJob.id, equalTo(createdJob.id))

    }

    private fun createClient(): Client {
        return ClientBuilder.newClient(ClientConfig(jacksonJaxbJsonProvider()))
    }


}