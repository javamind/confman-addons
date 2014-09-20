package com.ninjamind.confman.operation;

import com.google.gson.Gson;
import com.ninjamind.confman.dto.ConfmanDto;
import com.ninjamind.confman.utils.HttpCalls;
import com.ninjamind.confman.utils.Preconditions;

import java.util.Properties;

/**
 * Call cofman to read parameters values
 * @author Guillaume EHRET
 */
public class ConfmanReadParameterValues extends AbstractConfmanOperation<ConfmanReadParameterValues, Properties>{

    protected String appCode;
    protected String versionNumber;
    protected String envCode;
    protected String instanceCode;

    /**
     *
     * @param builder
     */
    private ConfmanReadParameterValues(Builder builder) {
        super(builder.server, builder.port);
        this.appCode = builder.appCode;
        this.versionNumber = builder.versionNumber;
        this.envCode = builder.envCode;
        this.instanceCode = builder.instanceCode;
    }

    /**
     * Read parameters for an environment, an application and a version.
     * <p>
     *  A configuration can be specific to one instance but instance is not a required property in the
     *  ApplicationConfig object. Indeed if you have only one application instance, it's not necessary to define an instance.
     * </p>
     * <p>
     *    If ApplicationConfig contains a code instance then Confman send the global parameters and the paramaters specific to this instance
     * </p>
     * <p>
     *    If ApplicationConfig not contains a code instance then Confman send the global parameters and the paramaters for all instances. Each
     *    instance parameters willbe suffixed by the instance code. For example
     *    <pre>
     *        server.name.CodeInstance1=Server1
     *    </pre>
     * </p>
     * @return all parameters in a {@link java.util.Properties}
     */
    @Override
    protected Properties executeAction() {
        //URL construction
        String url = String.format("http://%s:%s/confman/paramvalue/%s/version/%s/env/%s",server, port, appCode, versionNumber, envCode);

        //Confman is called
        String json = HttpCalls.get(url);

        Properties properties = new Properties();
        if(json!=null && !json.isEmpty()){
            //We use Gson to read the parameters values in the flow
            Gson gson = new Gson();
            ConfmanDto[] parameters = gson.fromJson(json, ConfmanDto[].class);

            for(ConfmanDto param : parameters){
                //A parameter can be linked ton an instance or not. If we ask a filter we keep the application parameters
                //and the parameters of the instance passed by the method arguments
                if(instanceCode!=null && (param.getCodeInstance()==null || param.getCodeInstance().equals(instanceCode))){
                    properties.put(param.getCode(), param.getLabel());
                }
                else if(instanceCode==null){
                    //If no filter is asked the parameters are suffixed by the instance code
                    properties.put(param.getCode().concat(param.getCodeInstance()!=null ? "." + param.getCodeInstance() : ""), param.getLabel());
                }
            }
        }
        return properties;
    }

    @Override
    protected void checkData() {
        Preconditions.checkNotNull(appCode, "application code is required");
        Preconditions.checkNotNull(versionNumber, "version number is required");
        Preconditions.checkNotNull(envCode, "environment code is required");
    }

    /**
     * Returns an operation which read all the parameters values for an applicaton in Confman
     * @param server param to call Confman
     */
    public static Builder from(String server) {
        return new Builder(server);
    }

    /**
     * A builder used to create this operation. Such a builder may only be used once. Once it has built its Insert
     * operation, all its methods throw an {@link IllegalStateException}.
     * @author Guillaume EHRET
     */
    public static final class Builder extends AbstractConfmanBuilder<Builder>{
        protected String appCode;
        protected String versionNumber;
        protected String envCode;
        protected String instanceCode;

        /**
         * Construct a new Builder.Set the server name use to call Confman server in the http request (http://server:port)
         * @param server
         */
        public Builder(String server) {
            super(server);
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
         * Set environment code
         * @param envCode
         * @return
         */
        public Builder env(String envCode) {
            Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
            this.envCode = envCode;
            return this;
        }

        /**
         * Set version number
         * @param versionNumber
         * @return
         */
        public Builder version(String versionNumber) {
            Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
            this.versionNumber = versionNumber;
            return this;
        }

        /**
         * Set a label for the parameter (APPLICATION or INSTANCE)
         * @param instanceCode
         * @return
         */
        public Builder instance(String instanceCode) {
            Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
            this.instanceCode = instanceCode;
            return this;
        }

        /**
         * Execute operation
         * @return
         * @throws IllegalStateException if the Insert has already been built, or if no column and no generated value
         * column has been specified.
         */
        public Properties execute() {
            Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
            built = true;
            return new ConfmanReadParameterValues(this).execute();
        }

    }

}
