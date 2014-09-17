package com.ninjamind.confman;

import com.beust.jcommander.Parameter;
import com.google.common.base.Strings;
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
public class ConfmanCommand {
    public static final String LINE_CHARACTER = "=";
    public static final int LINE_WIDTH = 75;

    @Parameter(names = "-type", required = true, description = "Object type : param, value, instance, version")

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
            ConfmanCommand command = new ConfmanCommand();
            command.displayVersion();
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
    public void displayVersion(){
        try(
                InputStream is = getClass().getClassLoader().getResource("version.txt").openStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
        ){
            //we open a stream, separe key and values and keep the line with the version
            Optional<String[]> version = br.lines().map(line -> line.split("=")).filter(line -> line[0].equals("version")).findAny();
            LOG.error("Version " + version.orElse(new String[]{"unknown","unknown"})[1]);
            LOG.info(Strings.repeat("=", 75));

            displayHelp();
        }
        catch (IOException | IndexOutOfBoundsException e){
            LOG.error("--> [error] impossible to read the version number " + e.getMessage());
        }
    }

    /**
     * Help for user
     */
    public void displayHelp(){
        LOG.info("");
        LOG.info("confman operation -type=[type] [options]");
        LOG.info("");
        LOG.info("The command line call the server Confman. The host and the name of the server");
        LOG.info("are defined in the configuration file confman.properties.");
        LOG.info("");
        LOG.info("Object Types");
        LOG.info("============");
        LOG.info("param      : Parameter defined for one application");
        LOG.info("instance   : Instance defined for one application");
        LOG.info("value      : Parameters values");
        LOG.info("version    : Application version");
        LOG.info("");
        LOG.info("Operations");
        LOG.info("==========");
        LOG.info("All the verbs are not defined for all the types... see the detailed documentation");
        LOG.info("bellow for more information");
        LOG.info("");
        LOG.info("read       : Read the object and print id, code, label...");
        LOG.info("create     : Create an object");
        LOG.info("update     : Update an object");
        LOG.info("");
        LOG.info("Options for one operation and one object (Format: -key=value)");
        LOG.info("=============================================================");
        LOG.info("");
        LOG.info("For object type [param] the verbs available are [read, create, update]");
        LOG.info(" -app                 : Application Code");
        LOG.info(" -code                : Parameter Code");
        LOG.info(" -label               : Parameter Label (not required for read)");
        LOG.info(" -typparam            : INSTANCE or APPLICATION (not required for read)");
        LOG.info("");
        LOG.info("For object type [instance] the verbs available are [read, create, update]");
        LOG.info(" -app                 : Application Code");
        LOG.info(" -code                : Instance Code");
        LOG.info(" -label               : Instance Label (not required for read)");
        LOG.info(" -regenerate          : If the value is true create a new set of parameters values (a tracking version)");
        LOG.info("");
        LOG.info("For object type [version] the verbs available are [read, create, update]");
        LOG.info(" -app                 : Application Code");
        LOG.info(" -code                : Version Code");
        LOG.info(" -label               : Version Label (not required for read)");
        LOG.info(" -regenerate          : If the value is true create a new set of parameters values (a tracking version)");
        LOG.info("");
        LOG.info("For object type [value] the verbs available are [read]");
        LOG.info(" -app                 : Application Code");
        LOG.info(" -env                 : Environment Code");
        LOG.info(" -version             : Version Code");
        LOG.info("");
        LOG.info("Example");
        LOG.info("=======");
        LOG.info("confman read -type=value -app=SALES -env=DEV -version=1.0.0");
        LOG.info("");
    }


}
