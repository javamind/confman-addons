package com.ninjamind.confman.conf;

import com.ninjamind.confman.service.Preconditions;

/**
 * An object which returns the appropriate datas to communicate with confman server. This object
 * use a builder to expose a nice API. If you want to create a ServerConfig you must use
 * <pre>
 *     ServerConfig.from(null).onPort(8080).build()
 * </pre>
 * The port 8080 is the default port.
 * @author Guillaume EHRET
 */
 public class ServerConfig implements ConfChecker<ServerConfig>{

    public static final int DEFAULT_PORT = 8080;
    private String server;
    private Integer port;

    private ServerConfig(Builder builder) {
        this.server = builder.server;
        this.port = builder.port!=null ? builder.port : DEFAULT_PORT;
    }

    /**
     * Creates a new Builder instance, in order to build an Insert operation into the given table
     * @param server
     * @return the created Builder
     */
    public static Builder from(String server) {
        Preconditions.checkNotNull(server, "server may not be null");
        return new Builder(server);
    }

    /**
     *
     * @return
     */
    public String getServer() {
        return server;
    }

    /**
     *
     * @return
     */
    public Integer getPort() {
        return port;
    }

    @Override
    public ServerConfig check() {
        Preconditions.checkNotNull(server, "server name is required");
        Preconditions.checkNotNull(port, "server port is required");
        return this;
    }

    /**
     * A builder used to create a Server config. Such a builder may only be used once. Once it has built its Insert
     * operation, all its methods throw an {@link IllegalStateException}.
     * @author Guillaume EHRET
     */
    public static final class Builder {
        public static final String ALREADY_BEEN_BUILT = "The server conf has already been built";
        private String server;
        private Integer port;
        private boolean built;

        /**
         * Construct a new Builder.Set the server name use to call Confman server in the http request (http://server:port)
         * @param server
         */
        public Builder(String server) {
            this.server = server;
        }

        /**
         * Set the server port number use to call Confman server in the http request (http://server:port)
         * @param port
         * @return
         */
        public Builder onPort(Integer port) {
            Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
            this.port = port;
            return this;
        }

        /**
         * Builds the Insert operation.
         * @return the created Insert operation.
         * @throws IllegalStateException if the Insert has already been built, or if no column and no generated value
         * column has been specified.
         */
        public ServerConfig build() {
            Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
            built = true;
            return new ServerConfig(this);
        }
    }
}
