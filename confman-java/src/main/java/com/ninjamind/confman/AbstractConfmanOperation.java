package com.ninjamind.confman;

import com.ninjamind.confman.utils.Preconditions;
import com.ninjamind.confman.utils.RestCallService;

/**
 * An operation that Confman will be execute.
 * @author Guillaume EHRET
 */
public abstract class AbstractConfmanOperation<O, T> {
    public static final int DEFAULT_PORT = 8080;
    public static final String DEFAULT_TYPE_PARAM = "APPLICATION";

    private RestCallService restCall;

    protected String appCode;
    protected String versionNumber;
    protected String envCode;
    protected String paramCode;
    protected String instanceCode;
    protected String label;
    protected String typeParameter;
    protected boolean generateWithLastParameterValuesSet;
    protected String server;
    protected Integer port;

    /**
     * Constuctor
     * @param restCall
     */
    protected AbstractConfmanOperation(RestCallService restCall, String server, Integer port) {
        this.restCall = restCall;
        this.server = server;
        this.port = port;
    }

    /**
     * Execute an operation in Confman
     * @return
     */
    public T execute() {
        Preconditions.checkNotNull(this.server, "server is required");
        Preconditions.checkNotNull(this.port, "server port is required");
        checkData();
        return executeAction();
    }

    /**
     *  Execute an operation in Confman
     *  @return
     */
    protected abstract T executeAction();

    /**
     *  Check datas before execute actions
     */
    protected abstract void checkData();

    /**
     * Create a Restcall object.
     * @return
     */
    protected RestCallService getRestCall() {
        if(restCall==null){
            restCall = new RestCallService();
        }
        return restCall;
    }

    /**
     * Injection in the tests
     * @param restCall
     */
    public O setRestCall(RestCallService restCall) {
        this.restCall = restCall;
        return (O) this;
    }



}
