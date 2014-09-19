package com.ninjamind.confman;

import com.google.gson.Gson;
import com.ninjamind.confman.dto.ConfmanDto;
import com.ninjamind.confman.utils.HttpCalls;
import com.ninjamind.confman.utils.Preconditions;

/**
 * Call cofman to read a parameter
 *
 * @author Guillaume EHRET
 */
public class ConfmanReadParameter extends AbstractConfmanOperation<ConfmanReadParameter, ConfmanDto> {

    protected String appCode;
    protected String paramCode;

    private ConfmanReadParameter(Builder builder) {
        super(builder.server, builder.port);
        this.appCode = builder.appCode;
        this.paramCode = builder.paramCode;
    }

    /**
     * Read a parameter to an application
     *
     * @return
     */
    @Override
    protected ConfmanDto executeAction() {
        //URL construction
        String url = String.format("http://%s:%s/confman/param/%s/app/%s", server, port, paramCode, appCode);

        //Confman is called
        String json = HttpCalls.get(url);
        if (json != null && !json.isEmpty()) {
            //We use Gson to read the parameters values in the flow
            Gson gson = new Gson();
            return gson.fromJson(json, ConfmanDto.class);
        }
        return null;
    }

    @Override
    protected void checkData() {
        Preconditions.checkNotNull(this.appCode, "application code is required");
        Preconditions.checkNotNull(this.paramCode, "parameter code is required");
    }

    /**
     * Returns an operation which read all the parameters values for an applicaton in Confman
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
        protected String paramCode;

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
         * Set a code for the parameter.
         *
         * @param paramCode
         * @return
         */
        public Builder code(String paramCode) {
            Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
            this.paramCode = paramCode;
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
            return new ConfmanReadParameter(this).execute();
        }
    }
}
