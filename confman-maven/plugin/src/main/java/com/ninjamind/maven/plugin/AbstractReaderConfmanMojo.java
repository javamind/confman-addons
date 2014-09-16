package com.ninjamind.maven.plugin;

import com.ninjamind.confman.ConfmanReadParameters;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Settings;

import java.util.*;


/**
 * This abstract goal call confman to read property and call a template method to execute
 * a batch
 *
 * @author Guillaume EHRET
 */
public abstract class AbstractReaderConfmanMojo extends AbstractMojo {

    /**
     * Confman server
     */
    @Parameter( name = "server", required = true)
    protected String server;

    /**
     * Application code
     */
    @Parameter( name = "app", required = true)
    protected String app;

    /**
     * Version number
     */
    @Parameter( name = "version", required = true)
    protected String version;

    /**
     *
     * Environment code
     */
    @Parameter( name = "env", required = true)
    protected String env;

    /**
     * Instance code
     */
    @Parameter( name = "instance", required = false)
    protected String instance;

    /**
     * Confman server port (efault 8080)
     */
    @Parameter( name = "port", defaultValue = "8080")
    protected Integer port;

    /**
     * Settings Maven
     */
    @Parameter( defaultValue = "${settings}", readonly = true )
    protected Settings settings;

    /**
     * Maven project which use this plugin
     */
    @Parameter( defaultValue = "${project}", readonly = true )
    protected MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Call Confman to display all the parameters");
        try{
            getLog().info(String.format("   Confman call on the URL http://%s:%s", server, port));
            getLog().info(String.format("   App=[%s] Version=[%s] Env=[%s] instance=[%s]", app, version, env, instance));
            Properties properties = ConfmanReadParameters
                    .from(ServerConfig.from(server).onPort(port).build())
                    .forApp(ApplicationConfig.application(app).version(version).env(env).instance(instance).build())
                    .execute();
            getLog().info(String.format("   %d properties read", properties.size()));

            if(properties.size()>0){
                //Properties are added to the project properties
                executeBatch(properties);
            }
        }
        catch (RuntimeException e){
            getLog().error("Error in confman plugin", e);
            throw e;
        }
        finally {
            getLog().info("The work of Confman is completed");
        }
    }

    /**
     * Execute a batch with the properties read in confman
     * @param properties
     * @throws MojoExecutionException
     */
    protected abstract void executeBatch(Properties properties) throws MojoExecutionException;


    /**
     * Return a list of properties code and value formated with the scheme specified
     * For example %s=%s will give
     * <pre>codeProperty=valueProperty</pre>
     *
     * @param properties
     * @throws MojoExecutionException
     */
    protected List<String> formatProperties(Properties properties, String scheme) {
        List<String> props = new ArrayList<String>(properties.size());
        for(Map.Entry<Object, Object> property : properties.entrySet()){
            props.add(String.format(scheme, property.getKey(), property.getValue()));
        }
        Collections.sort(props, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        return props;
    }
}
