package com.ninjamind.confman.operation;

import com.google.gson.Gson;
import com.ninjamind.confman.dto.ConfmanDto;
import com.ninjamind.confman.utils.HttpCalls;
import com.ninjamind.confman.utils.Preconditions;

/**
 * Call confman to read a instance
 *
 * @author Guillaume EHRET
 */
public class ConfmanReadInstance extends AbstractConfmanOperation<ConfmanReadInstance, ConfmanDto> {

    protected String appCode;
    protected String instanceCode;
    protected String envCode;

    private ConfmanReadInstance(Builder builder) {
        super(builder.server, builder.port);
        this.appCode = builder.appCode;
        this.instanceCode = builder.instanceCode;
        this.envCode = builder.envCode;
    }

    /**
     * Read a instance to an application
     *
     * @return
     */
    @Override
    protected ConfmanDto executeAction() {
        //URL construction
        String url = String.format("http://%s:%s/confman/instance/%s/app/%s/env/%s", server, port, instanceCode, appCode, envCode);

        //Confman is called
        String json = HttpCalls.get(url);
        if (json != null && !json.isEmpty()) {
            //We use Gson to read the instances values in the flow
            Gson gson = new Gson();
            return gson.fromJson(json, ConfmanDto.class);
        }
        return null;
    }

    @Override
    protected void checkData() {
        Preconditions.checkNotNull(this.appCode, "application code is required");
        Preconditions.checkNotNull(this.instanceCode, "instance code is required");
        Preconditions.checkNotNull(this.envCode, "environment code is required");
    }

    /**
     * Returns an operation which read all the instances values for an applicaton in Confman
     *
     * @param server
     */
    public static Builder from(String server) {
        return new Builder(server);
    }


    /**
     * A builder used to create this operation. Such a builder may only be used once. Once it has built its Insert
     * operation, all its methods throw an {@link IllegalStateException}.
     *
     * @author Guillaume EHRET
     */
    public static final class Builder extends AbstractConfmanBuilder<Builder> {
        protected String appCode;
        protected String instanceCode;
        protected String envCode;

        /**
         * Construct a new Builder.Set the server name use to call Confman server in the http request (http://server:port)
         *
         * @param server
         */
        public Builder(String server) {
            super(server);
        }

        /**
         * Set application code.
         *
         * @param appCode
         * @return
         */
        public Builder forApp(String appCode) {
            Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
            this.appCode = appCode;
            return this;
        }

        /**
         * Set a code for the instance.
         *
         * @param instanceCode
         * @return
         */
        public Builder code(String instanceCode) {
            Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
            this.instanceCode = instanceCode;
            return this;
        }

        /**
         * Set a code for the environment.
         *
         * @param envCode
         * @return
         */
        public Builder env(String envCode) {
            Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
            this.envCode = envCode;
            return this;
        }

        /**
         * Execute operation
         * @return
         * @throws IllegalStateException if the Insert has already been built, or if no column and no generated value
         * column has been specified.
         */
        public ConfmanDto execute() {
            Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
            built = true;
            return new ConfmanReadInstance(this).execute();
        }
    }
}
