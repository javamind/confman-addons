package com.ninjamind.confman;

import com.ninjamind.confman.conf.ApplicationConfig;
import com.ninjamind.confman.conf.ServerConfig;
import com.ninjamind.confman.service.RestCall;

/**
 * @author Guillaume EHRET
 */
public abstract class AbstractConfmanOperation<O, T> implements Operation<T> {
    protected RestCall restCall;
    protected ServerConfig serverConfig;
    protected ApplicationConfig appConfmanConfig;


    /**
     * @param serverConfig
     */
    protected AbstractConfmanOperation(ServerConfig serverConfig) {
        serverConfig.check();
        this.serverConfig = serverConfig;
    }

    /**
     * Create a Restcall object.
     * @return
     */
    protected RestCall getRestCall() {
        if(restCall==null){
            restCall = new RestCall();
        }
        return restCall;
    }

    /**
     * Injection in the tests
     * @param restCall
     */
    public O setRestCall(RestCall restCall) {
        this.restCall = restCall;
        return (O) this;
    }



}
