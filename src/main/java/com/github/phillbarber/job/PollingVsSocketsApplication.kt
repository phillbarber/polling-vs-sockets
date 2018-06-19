package com.github.phillbarber.job;

import com.google.common.io.Resources
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlet.ServletHolder
import java.io.File
import java.net.URISyntaxException

//This main method doesn't seem to get compiled in as a static method - so had to add Launcher object
fun main(args: Array<String>) {
    if(args.size == 0){
        PollingVsSocketsApplication().run("server", getFileFromClassPath("config-default.yml").absolutePath);
    }
    else{
        PollingVsSocketsApplication().run(*args);
    }
}


fun getFileFromClassPath(file: String): File {
    try {
        return File(Resources.getResource(file).toURI())
    } catch (e: URISyntaxException) {
        throw RuntimeException(e)
    }

}

class PollingVsSocketsApplication : Application<PollingVsSocketsConfiguration>() {



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
