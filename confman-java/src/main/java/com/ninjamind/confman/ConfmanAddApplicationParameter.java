package com.ninjamind.confman;

import com.ninjamind.confman.conf.ApplicationConfig;
import com.ninjamind.confman.conf.ServerConfig;
import com.ninjamind.confman.service.Preconditions;

/**
 * Call cofman to read parameters values
 * @author Guillaume EHRET
 */
public class ConfmanAddApplicationParameter extends AbstractConfmanOperation<ConfmanAddApplicationParameter, Void>{

    /**
     * This object is created via the method {@link com.ninjamind.confman.ConfmanAddApplicationParameter#from(com.ninjamind.confman.conf.ServerConfig)}.
     * The aim is a more beautiful api
     * @param serverConfig
     */
    private ConfmanAddApplicationParameter(ServerConfig serverConfig) {
        super(serverConfig);
    }

    /**
     * Add a parameter to an application
     * @return
     */
    @Override
    public Void execute() {
        appConfmanConfig.check();
        Preconditions.checkNotNull(appConfmanConfig.getParamCode(), "parameter code is required");
        Preconditions.checkNotNull(appConfmanConfig.getValue(), "parameter value is required");

        return null;
    }

    /**
     * Returns an operation which read all the parameters values for an applicaton in Confman
     * @param serverConfig param to call Confman
     */
    public static ConfmanAddApplicationParameter from(ServerConfig serverConfig) {
        return new ConfmanAddApplicationParameter(serverConfig);
    }

    /**
     * Complete the operation with information to describe the target application. You have to give
     * <ul>
     *     <li>application code</li>
     *     <li>parameter code</li>
     *     <li>label</li>
     * </ul>
     * For example
     * <pre>
     *     ApplicationConfig.application("APP").param("PARAM1").value("myvalue").build()
     * </pre>
     * @param appConfmanConfig param to define an application
     */
    public ConfmanAddApplicationParameter forApp(ApplicationConfig appConfmanConfig) {
        this.appConfmanConfig = appConfmanConfig;
        return this;
    }
}
