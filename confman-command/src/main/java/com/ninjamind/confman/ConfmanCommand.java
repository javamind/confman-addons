package com.ninjamind.confman;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.gson.JsonSyntaxException;
import com.ninjamind.confman.dto.ConfmanDto;
import com.ninjamind.confman.validator.ObjectTypeValidator;
import com.ninjamind.confman.validator.ParameterTypeValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.LoggerConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

/**
 * Entry point of the Confman command-line tool
 * @author Guillaume EHRET
 */
@Parameters(separators = "=")
public class ConfmanCommand {
    public static final String LINE_CHARACTER = "=";
    public static final int LINE_WIDTH = 75;

    @Parameter(names = {"-o", "--object"}, required = true, description = "Object type : param, value, instance, version", validateWith = ObjectTypeValidator.class)
    private String objectType;

    @Parameter(names = {"-h", "--help"}, help = true, description = "Print help")
    private boolean help = false;

    @Parameter(names = "-read", description = "Read datas")
    private boolean read = false;

    @Parameter(names = "-create", description = "Create datas")
    private boolean create = false;

    @Parameter(names = "-update", description = "Update datas")
    private boolean update = false;

    @Parameter(names = {"-t", "--typparam"}, description = "Parameter type (INSTANCE or APPLICATION)", validateWith = ParameterTypeValidator.class)
    private String typparam;

    @Parameter(names = {"-a", "--app"}, description = "Application code")
    private String app;

    @Parameter(names = {"-cd", "--code"}, description = "Code")
    private String code;

    @Parameter(names = {"-lb", "--label"}, description = "Label")
    private String label;

    @Parameter(names = {"-e", "--env"}, description = "Environment code")
    private String env;

    @Parameter(names = {"-g", "--regenerate"}, description = "Create a new set of parameters values (a tracking version)")
    private String regenerate;

    @Parameter(names = {"-v", "--version"}, description = "Version code")
    private String version;

    @Parameter(names = {"-i", "--instance"}, description = "Instance code")
    private String instance;

    /**
     * This data is not give as an argument but read in a conf file
     */
    private String server;
    /**
     * This data is not give as an argument but read in a conf file
     */
    private Integer port;

    /**
     * Logger
     */
    private static Logger LOG = LogManager.getLogger();

    private static Properties properties;

    /**
     * Start Endpoint
     * @param args
     */
    public static void main(String[] args) {

        try{
            ConfmanCommand  confmanCommand = new ConfmanCommand();


            //We load the properties
            try(
                    InputStream is = confmanCommand.getClass().getClassLoader().getResource("confman.properties").openStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
            ) {
                properties = new Properties();
                properties.load(is);
            }
            catch (IOException e){
                LOG.error("--> [error] impossible to read the file application.properties " + e.getMessage());
            }

            LOG.info(Strings.repeat(LINE_CHARACTER, LINE_WIDTH));
            LOG.error("             CONFMAN COMMAND LINE " + properties.getProperty("version"));
            LOG.info(Strings.repeat(LINE_CHARACTER, LINE_WIDTH));


            //The paramaters are interpreted
            new JCommander(confmanCommand, args);



            //We execute the command
            confmanCommand.execute();
        }
        catch (JsonSyntaxException e){
            LOG.error("no data found");
        }
        catch (RuntimeException e) {
            LOG.error("Unexpected error", e);
            System.exit(1);
        }
        finally{
            LOG.info(Strings.repeat(LINE_CHARACTER, LINE_WIDTH));
        }
    }

    /**
     * Print the version of the command line
     */
    public void execute(){
        port = Integer.valueOf(Objects.firstNonNull(properties.getProperty("confman.server.port"), "8080"));
        server = properties.getProperty("confman.server.name");

        if(read || update || create){
            //The operation is specific for an object type
            switch (objectType){
                case ObjectTypeValidator.OBJECT_INSTANCE:
                    executeInstance();
                    break;
                case ObjectTypeValidator.OBJECT_PARAM:
                    executeParam();
                    break;
                case ObjectTypeValidator.OBJECT_VALUE:

                    break;
                case ObjectTypeValidator.OBJECT_VERSION:
                   executeVersion();
            }
        }
        else{
            LOG.info("HELP ");
            LOG.info("====");
            printUsage();
        }
    }

    /**
     * Operations for parameters values
     */
    public void executeParametersValues(){
        if(read){
            Properties props = Operations
                    .readValues(properties.getProperty("confman.server.name"))
                    .onPort(port)
                    .forApp(app)
                    .version(code)
                    .instance(instance)
                    .execute();

            LOG.info("Read parameters values");
            for(Map.Entry<Object,Object> prop : props.entrySet()) {
                LOG.info(String.format("... code=[%s] label=[%s] app=[%s] instance=[%s]", prop.getKey(), prop.getValue(), app, instance));
            }
        }
    }

    /**
     * Operations for version
     */
    public void executeVersion(){
        if(read){
            ConfmanDto dto = Operations
                    .readVersion(server)
                    .onPort(port)
                    .forApp(app)
                    .version(code)
                    .execute();

            LOG.info("Read version");
            LOG.info(String.format("... code=[%s] label=[%s] app=[%s]", dto.getCode(), dto.getLabel(), dto.getCodeApplication()));
        }
        else if(create){
            Operations.addParameter(server)
                    .onPort(port)
                    .code(code)
                    .forApp(app)
                    .label(label)
                    .type(typparam);
        }
    }

    /**
     * Operations for param
     */
    public void executeParam(){
        if(read){
            ConfmanDto dto = Operations
                    .readParameter(server)
                    .onPort(port)
                    .forApp(app)
                    .code(code)
                    .execute();

            LOG.info("Read parameter");
            LOG.info(String.format("... code=[%s] label=[%s] app=[%s]", dto.getCode(), dto.getLabel(), dto.getCodeApplication()));
        }
    }

    /**
     * Operations for instance
     */
    public void executeInstance(){
        if(read){
            ConfmanDto dto = Operations
                    .readInstance(server)
                    .onPort(port)
                    .forApp(app)
                    .code(code)
                    .env(env)
                    .execute();

            LOG.info("Read instance");
            LOG.info(String.format("... code=[%s] label=[%s] app=[%s] env=[%s]", dto.getCode(), dto.getLabel(), dto.getCodeApplication(), dto.getCodeEnvironment()));
        }
    }

    /**
     * Help for user
     */
    public void printUsage(){
        LOG.info("");
        LOG.info("confman -[operation] --object=[type] [options]");
        LOG.info("");
        LOG.info("The command line call the server Confman. The host and the name of the server");
        LOG.info("are defined in the configuration file confman.properties.");
        LOG.info("");
        LOG.info("Object Types ");
        LOG.info("============");
        LOG.info(" -o=param      : Parameter defined for one application");
        LOG.info(" -o=instance   : Instance defined for one application");
        LOG.info(" -o=value      : Parameters values");
        LOG.info(" -o=version    : Application version");
        LOG.info("");
        LOG.info("Operations");
        LOG.info("==========");
        LOG.info("All the verbs are not defined for all the types... see the detailed documentation");
        LOG.info("bellow for more information");
        LOG.info("");
        LOG.info(" -read       : Read the object and print id, code, label...");
        LOG.info(" -create     : Create an object");
        LOG.info(" -update     : Update an object");
        LOG.info("");
        LOG.info("Options for one operation and one object (Format: -key=value)");
        LOG.info("=============================================================");
        LOG.info("");
        LOG.info("For object type [param] the verbs available are [read, create, update]");
        LOG.info(" -a, --app                 : Application Code");
        LOG.info(" -c, --code                : Parameter Code");
        LOG.info(" -l, --label               : Parameter Label (not required for read)");
        LOG.info(" -t, --typparam            : INSTANCE or APPLICATION (not required for read)");
        LOG.info("");
        LOG.info("For object type [instance] the verbs available are [read, create, update]");
        LOG.info(" -a, --app                 : Application Code");
        LOG.info(" -c, --code                : Instance Code");
        LOG.info(" -l, --label               : Instance Label (not required for read)");
        LOG.info("");
        LOG.info("For object type [version] the verbs available are [read, create, update]");
        LOG.info(" -a, --app                 : Application Code");
        LOG.info(" -c, --code                : Version Code");
        LOG.info(" -l, --label               : Version Label (not required for read)");
        LOG.info(" -g, --regenerate          : If the value is true create a new set of parameters values (a tracking version)");
        LOG.info("");
        LOG.info("For object type [value] the verbs available are [read]");
        LOG.info(" -a, --app                 : Application Code");
        LOG.info(" -e, --env                 : Environment Code");
        LOG.info(" -v, --version             : Version Code");
        LOG.info(" -i, --instance            : Instance Code");
        LOG.info("");
        LOG.info("Example");
        LOG.info("=======");
        LOG.info("confman -read --object=value --app=SALES --env=DEV --version=1.0.0");
        LOG.info("confman -read -o=value -a=SALES -e=DEV -v=1.0.0");
        LOG.info("");
    }


}
