package com.ninjamind.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * This plugin is used to display help for all the sub plugins
 */
@Mojo(
        name = "help",
        defaultPhase = LifecyclePhase.INITIALIZE
)
@Execute(
        goal = "help",
        phase = LifecyclePhase.INITIALIZE
)
public class DisplayConfmanHelpMojo extends AbstractMojo {


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        getLog().info("The maven plugin Confman can be used to dialog with your Confman server");
        getLog().info("Several goals are available");
        getLog().info("-----------------------------------------------------------------------");
        getLog().info("   confman-help : to see all the possibilities");
        getLog().info("     -> no parameter is required");
        getLog().info("-----------------------------------------------------------------------");
        getLog().info("   confman-display-properties : to display in the console log all the parameters value");
        getLog().info("     -> server : server where Confman is installed");
        getLog().info("     -> [port] : server port (default is 8080)");
        getLog().info("     -> app : application code defined in  Confman");
        getLog().info("     -> env : your install is for one environment. Tou have to give the environment code");
        getLog().info("     -> version : all yours versions have to be defined in Confman. The number help to find the values");
        getLog().info("     -> [instance] : if you have several instance for one environment");
        getLog().info("-----------------------------------------------------------------------");
        getLog().info("   confman-write-properties : to display in the console log all the parameters value");
        getLog().info("     -> server : server where Confman is installed");
        getLog().info("     -> [port] : server port (default is 8080)");
        getLog().info("     -> app : application code defined in  Confman");
        getLog().info("     -> env : your install is for one environment. Tou have to give the environment code");
        getLog().info("     -> version : all yours versions have to be defined in Confman. The number help to find the values");
        getLog().info("     -> [instance] : if you have several instance for one environment");
        getLog().info("     -> [encoding] : default is '${project.build.sourceEncoding}'");
        getLog().info("     -> [propertiespath] : default is '${basedir}/src/main/filters/'");
        getLog().info("     -> [propertiesprefix] : default is 'filter-'");
        getLog().info("     -> [propertiessuffix] : default is ''");
        getLog().info("-----------------------------------------------------------------------");
        getLog().info("   confman-read-properties : to load the parameters value in the maven context");
        getLog().info("     -> server : server where Confman is installed");
        getLog().info("     -> [port] : server port (default is 8080)");
        getLog().info("     -> app : application code defined in  Confman");
        getLog().info("     -> env : your install is for one environment. Tou have to give the environment code");
        getLog().info("     -> version : all yours versions have to be defined in Confman. The number help to find the values");
        getLog().info("     -> [instance] : if you have several instance for one environment");
        getLog().info("-----------------------------------------------------------------------");

    }
}
