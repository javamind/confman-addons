package com.ninjamind.confman;

import com.ninjamind.confman.utils.Preconditions;
import com.ninjamind.confman.utils.RestCallService;

/**
 * Call cofman to read parameters values
 * @author Guillaume EHRET
 */
public class ConfmanAddApplicationParameter extends AbstractConfmanOperation<ConfmanAddApplicationParameter, Void>{


    private ConfmanAddApplicationParameter(Builder builder){
        super(builder.restCall, builder.server, builder.port);
        this.appCode = builder.appCode;
        this.paramCode = builder.paramCode;
        this.label = builder.paramLabel;
        this.typeParameter = builder.typeParameter;
    }

    /**
     * Add a parameter to an application
     * @return
     */
    @Override
    protected Void executeAction() {

        return null;
    }

    @Override
    protected void checkData() {
        Preconditions.checkNotNull(this.appCode, "application code is required");
        Preconditions.checkNotNull(this.paramCode, "parameter code is required");
        Preconditions.checkNotNull(this.label, "label is required");
        Preconditions.checkNotNull(this.typeParameter, "parameter type is required");
    }

    /**
     * Returns an operation which read all the parameters values for an applicaton in Confman
     * @param server
     */
    public static Builder from(String server) {
        return new Builder(server);
    }



    /**
     * A builder used to create this operation. Such a builder may only be used once. Once it has built its Insert
     * operation, all its methods throw an {@link IllegalStateException}.
     * @author Guillaume EHRET
     */
    public static final class Builder {
        public static final String ALREADY_BEEN_BUILT = "The ConfmanAddApplicationParameter has already been built";
        protected String server;
        protected Integer port = DEFAULT_PORT;
        private boolean built;
        protected String appCode;
        protected String paramCode;
        protected String paramLabel;
        protected String typeParameter = DEFAULT_TYPE_PARAM;
        protected RestCallService restCall;

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
         * Set application code.
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
         * @param paramCode
         * @return
         */
        public Builder code(String paramCode) {
            Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
            this.paramCode = paramCode;
            return this;
        }

        /**
         * Set a label for the parameter.
         * @param paramLabel
         * @return
         */
        public Builder label(String paramLabel) {
            Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
            this.paramLabel = paramLabel;
            return this;
        }

        /**
         * Set a label for the parameter (APPLICATION or INSTANCE)
         * @param typeParameter
         * @return
         */
        public Builder type(String typeParameter) {
            Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
            if(typeParameter!=null &&
                    (DEFAULT_TYPE_PARAM.equals(typeParameter.toUpperCase()) || "INSTANCE".equals(typeParameter.toUpperCase()))){
                this.typeParameter = typeParameter;
            }
            return this;
        }

        /**
         * Builds the ApplicationConfig.
         * @return
         * @throws IllegalStateException if the Insert has already been built, or if no column and no generated value
         * column has been specified.
         */
        public void execute() {
            Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
            built = true;
            new ConfmanAddApplicationParameter(this).execute();
        }

        /**
         * Use in a text context
         * @param restCall
         */
        public ConfmanAddApplicationParameter build(RestCallService restCall) {
            Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
            built = true;
            this.restCall = restCall;
            return new ConfmanAddApplicationParameter(this);
        }
    }
}
