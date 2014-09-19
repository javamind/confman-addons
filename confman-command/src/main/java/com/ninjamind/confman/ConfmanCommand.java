package com.ninjamind.confman;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.google.common.base.Strings;
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
import java.util.Optional;

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

    /**
     * Logger associé
     */
    private static Logger LOG = LogManager.getLogger();

    /**
     * Start Endpoint
     * @param args
     */
    public static void main(String[] args) {
        LOG.info(Strings.repeat(LINE_CHARACTER, LINE_WIDTH));
        LOG.error("             CONFMAN COMMAND LINE");
        LOG.info(Strings.repeat(LINE_CHARACTER, LINE_WIDTH));

        try{
            ConfmanCommand  confmanCommand = new ConfmanCommand();
            new JCommander(confmanCommand, args);
            confmanCommand.run();
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
    public void run(){
        printVersion();

        if(read || update || create){
            //The operation is specific for an object type
            switch (objectType){
                case ObjectTypeValidator.OBJECT_INSTANCE:

                    break;
                case ObjectTypeValidator.OBJECT_PARAM:
                    break;
                case ObjectTypeValidator.OBJECT_VALUE:
                    break;
                case ObjectTypeValidator.OBJECT_VERSION:

            }
        }
        else{
            LOG.info("HELP ");
            LOG.info("====");
            printUsage();
        }
    }

    /**
     * Print the version of the command line
     */
    public void printVersion(){
        try(
                InputStream is = getClass().getClassLoader().getResource("version.txt").openStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
        ){
            //we open a stream, separe key and values and keep the line with the version
            Optional<String[]> version = br.lines().map(line -> line.split("=")).filter(line -> line[0].equals("version")).findAny();
            LOG.error("Version " + version.orElse(new String[]{"unknown","unknown"})[1]);
            LOG.info(Strings.repeat("=", 75));
        }
        catch (IOException | IndexOutOfBoundsException e){
            LOG.error("--> [error] impossible to read the version number " + e.getMessage());
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
        LOG.info("");
        LOG.info("Example");
        LOG.info("=======");
        LOG.info("confman -read --object=value --app=SALES --env=DEV --version=1.0.0");
        LOG.info("confman -read -o=value -a=SALES -e=DEV -v=1.0.0");
        LOG.info("");
    }


}
