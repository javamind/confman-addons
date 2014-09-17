package com.ninjamind.confman;

import com.google.common.base.Strings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.LoggerConfig;

/**
 * Entry point of the Confman command-line tool
 * @author Guillaume EHRET
 */
public class ConfmanCommand {
    /**
     * Logger associé
     */
    private static Logger LOG = LogManager.getLogger();

    /**
     * Start Endpoint
     * @param args
     */
    public static void main(String[] args) {
        LOG.info(Strings.repeat("-", 75));
        LOG.error(" CONFMAN COMMAND LINE");
        LOG.info(Strings.repeat("-", 75));

        try{
            ConfmanCommand command = new ConfmanCommand();
        }
        catch (RuntimeException e) {
            LOG.error("Unexpected error", e);
            System.exit(1);
        }
        finally{
            LOG.info(Strings.repeat("=", 75));
            LOG.error("= Confman Command Line");
            LOG.info(Strings.repeat("=", 75));
        }
    }

    public void run(String[] args){

    }
}
