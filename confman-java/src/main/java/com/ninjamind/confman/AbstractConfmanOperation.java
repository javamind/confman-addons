package com.ninjamind.confman;

import com.ninjamind.confman.utils.Preconditions;

/**
 * An operation that Confman will be execute.
 * @author Guillaume EHRET
 */
public abstract class AbstractConfmanOperation<O, T> {
    public static final int DEFAULT_PORT = 8080;
    public static final String DEFAULT_TYPE_PARAM = "APPLICATION";

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
     * @param server confman
     * @param port of the server
     */
    protected AbstractConfmanOperation(String server, Integer port) {
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




}
