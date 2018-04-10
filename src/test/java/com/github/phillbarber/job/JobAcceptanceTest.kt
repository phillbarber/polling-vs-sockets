package com.github.phillbarber.job

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.phillbarber.Job
import com.github.phillbarber.PollingVsSocketsApplication
import com.github.phillbarber.PollingVsSocketsConfiguration
import com.github.phillbarber.getFileFromClassPath
import io.dropwizard.testing.junit.DropwizardAppRule
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.MediaType
import org.hamcrest.CoreMatchers.`is` as _is
import org.glassfish.jersey.client.ClientConfig
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider
import javax.ws.rs.client.Client


class JobAcceptanceTest {

    val appRule: DropwizardAppRule<PollingVsSocketsConfiguration> = DropwizardAppRule(PollingVsSocketsApplication::class.java,
            getFileFromClassPath("config.yml").getAbsolutePath())


    @Rule
    fun getTestRule(): DropwizardAppRule<PollingVsSocketsConfiguration> = appRule

    var client = createClient()

    @Test
    fun nonExistentJobReturns404(){
        var get = client.target("http://localhost:8080/job")
                .request(MediaType.APPLICATION_JSON)
                .get()
        assertThat(get.status, _is(404))
    }

    @Test
    fun createJob() {
        var response = client.target("http://localhost:8080/job")
                .request(MediaType.APPLICATION_JSON)
                .post(null)
        var entity = response.readEntity(Job::class.java)
        assertThat(response.status, _is(201))
        assertThat(entity.id, _is(notNullValue()))
    }

    private fun createClient(): Client {
        val jacksonProvider = JacksonJaxbJsonProvider()
        val mapper = ObjectMapper().registerModule(KotlinModule())
        jacksonProvider.setMapper(mapper)
        val client = ClientBuilder.newClient(ClientConfig(jacksonProvider))
        return client
    }
}