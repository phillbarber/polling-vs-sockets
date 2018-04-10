package com.github.phillbarber;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
        environment.jersey().register(JobResource());
    }

}
