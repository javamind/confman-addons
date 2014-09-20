package com.ninjamind.confman.operation;

import com.ninjamind.confman.utils.Preconditions;

/**
 * An operation that Confman will be execute.
 * @author Guillaume EHRET
 */
public abstract class AbstractConfmanBuilder<T extends AbstractConfmanBuilder> {
    public static final String ALREADY_BEEN_BUILT = "The object has already been built";
    protected String server;
    protected Integer port = AbstractConfmanOperation.DEFAULT_PORT;
    protected boolean built;

    /**
     * Construct a new Builder.Set the server name use to call Confman server in the http request (http://server:port)
     * @param server
     */
    public AbstractConfmanBuilder(String server) {
        this.server = server;
    }

    /**
     * Set the server port number use to call Confman server in the http request (http://server:port)
     * @param port
     * @return
     */
    public T onPort(Integer port) {
        Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
        this.port = port;
        return (T)this;
    }

}
