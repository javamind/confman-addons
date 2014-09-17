package com.ninjamind.maven.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.PropertyUtils;

import java.util.*;


/**
 * This plugin is used to display Confman properties in console
 *
 * @author Guillaume EHRET
 */
@Mojo(
        name = "display-properties",
        defaultPhase = LifecyclePhase.INITIALIZE
)
@Execute(
        goal = "display-properties",
        phase = LifecyclePhase.INITIALIZE
)
public class DisplayConfmanPropertiesMojo extends AbstractReaderConfmanMojo {



    /**
     *
     * @param properties
     * @throws MojoExecutionException
     */
    @Override
    protected void executeBatch(Properties properties) throws MojoExecutionException {
        for(String elt : formatProperties(properties, "    - %s : %s ")){
            getLog().info(elt);
        }
    }
}
