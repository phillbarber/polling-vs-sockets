package com.github.phillbarber;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class PollingVsSocketsApplication extends Application<PollingVsSocketsConfiguration> {

    public static void main(final String[] args) throws Exception {
        new PollingVsSocketsApplication().run(args);
    }

    @Override
    public String getName() {
        return "PollingVsSockets";
    }

    @Override
    public void initialize(final Bootstrap<PollingVsSocketsConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final PollingVsSocketsConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
