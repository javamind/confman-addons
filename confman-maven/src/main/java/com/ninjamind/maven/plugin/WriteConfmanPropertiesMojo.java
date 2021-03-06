package com.ninjamind.maven.plugin;

import com.ninjamind.confman.dto.ParameterValueConfmanDto;
import com.ninjamind.maven.plugin.util.ConfmanFileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


/**
 * This plugin is used to display Confman properties in console
 *
 * @author Guillaume EHRET
 */
@Mojo(
        name = "write-properties",
        defaultPhase = LifecyclePhase.INITIALIZE
)
@Execute(
        goal = "write-properties",
        phase = LifecyclePhase.INITIALIZE
)
public class WriteConfmanPropertiesMojo extends AbstractReaderConfmanMojo {

    /**
     * The encoding to use when storing the properties (default is ${project.build.sourceEncoding})
     */
    @Parameter(name = "encoding", defaultValue = "${project.build.sourceEncoding}")
    String encoding;

    /**
     * Path where properties files will be created (default is ${basedir}/src/main/filters/ in basedir)
     */
    @Parameter(name = "propertiespath", defaultValue = "${basedir}/src/main/filters/")
    private String propertiespath;

    /**
     * Prefix of the properties file (default is filter-). After this prefix the plugin
     * add the environment code and instance code if instance is an argument. For exemple
     * is you define env=DEV and instance=MyServer1 the default name will be
     * <pre>
     *     filter-dev-myserver1.properties
     * </pre>
     */
    @Parameter(name = "propertiesprefix", defaultValue = "filter-")
    private String propertiesprefix;

    /**
     * Suffix of the properties file (default is "").
     * <pre>
     *     filter-dev-myserver1.properties
     * </pre>
     */
    @Parameter(name = "propertiessuffix")
    private String propertiessuffix;


    /**
     * @param properties
     * @throws org.apache.maven.plugin.MojoExecutionException
     */
    @Override
    protected void executeBatch(ParameterValueConfmanDto[] properties) throws MojoExecutionException {
        ConfmanFileUtils.writePropertiesInFile(
                propertiespath,
                propertiesprefix,
                propertiessuffix,
                env,
                getLog(),
                encoding!=null ? Charset.forName(encoding) : StandardCharsets.UTF_8,
                formatProperties(properties, "#%s\n%s=%s")
        );
    }
}
