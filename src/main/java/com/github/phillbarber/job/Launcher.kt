package com.github.phillbarber.job


/*
For some reason the main method would not work from within PollingVsSocketsApplication - had to add this.
 */

object Launcher {
    @JvmStatic fun main(args: Array<String>) {
        if(args.size == 0){
            PollingVsSocketsApplication().run("server");
        }
        else{
            PollingVsSocketsApplication().run(*args);
        }
    }
}