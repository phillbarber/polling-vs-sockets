package com.github.phillbarber.job

import com.github.phillbarber.PollingVsSocketsApplication
import com.github.phillbarber.PollingVsSocketsConfiguration
import com.github.phillbarber.getFileFromClassPath
import io.dropwizard.testing.junit.DropwizardAppRule
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.MediaType
import org.hamcrest.CoreMatchers.`is` as _is


class JobAcceptanceTest {

/*
POST /job
{Id:12334}
job-id

GET /job/id
{Id:1234, complete:false}

 */

    val appRule: DropwizardAppRule<PollingVsSocketsConfiguration> = DropwizardAppRule(PollingVsSocketsApplication::class.java,
            getFileFromClassPath("config.yml").getAbsolutePath())


    @Rule
    public fun getTestRule(): DropwizardAppRule<PollingVsSocketsConfiguration> = appRule

    @Test
    fun nonExistentJobReturns404(){
        var client = ClientBuilder.newClient()
        var get = client.target("http://localhost:8080/job")
                .request(MediaType.APPLICATION_JSON)
                .get()
        assertThat(get.status, _is(404))
    }

}