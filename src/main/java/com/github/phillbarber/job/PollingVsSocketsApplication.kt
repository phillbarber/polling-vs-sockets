package com.github.phillbarber.job;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlet.ServletHolder

class PollingVsSocketsApplication : Application<PollingVsSocketsConfiguration>() {

    fun main(args : Array<String>) {
        PollingVsSocketsApplication().run(*args);
    }

    override fun getName():String {
        return "PollingVsSockets";
    }

    override fun initialize(b: Bootstrap<PollingVsSocketsConfiguration>) {
        // TODO: application initialization
    }

    override fun run(configuration: PollingVsSocketsConfiguration, environment: Environment) {
        environment.jersey().register(JobResource(JobService()))
        environment.getApplicationContext().getServletHandler().addServletWithMapping(ServletHolder(MyEchoServlet()), "/echo");
        environment.getApplicationContext().getServletHandler().addServletWithMapping(ServletHolder(JobServlet()), "/ws/job");
    }

}
