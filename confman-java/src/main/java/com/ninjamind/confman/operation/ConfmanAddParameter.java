package com.ninjamind.confman.operation;

import com.google.gson.Gson;
import com.ninjamind.confman.dto.ConfmanDto;
import com.ninjamind.confman.utils.HttpCalls;
import com.ninjamind.confman.utils.Preconditions;

import java.util.HashMap;
import java.util.Map;

/**
 * Call cofman to add a parameter to an application
 *
 * @author Guillaume EHRET
 */
public class ConfmanAddParameter extends AbstractConfmanOperation<ConfmanAddParameter, Void> {

    protected String appCode;
    protected String paramCode;
    protected String label;
    protected String typeParameter;

    private ConfmanAddParameter(Builder builder) {
        super(builder.server, builder.port);
        this.appCode = builder.appCode;
        this.paramCode = builder.paramCode;
        this.label = builder.paramLabel;
        this.typeParameter = builder.typeParameter;
    }

    /**
     * Add a parameter to an application
     *
     * @return
     */
    @Override
    protected Void executeAction() {
        //URL construction
        String url = String.format("http://%s:%s/api/param/%s/app/%s", server, port, paramCode, appCode);

        //Parameters preparation
        //Note : this project is in Java6 we can't use diamond
        Map<String, String> map = new HashMap<String, String>();
        map.put(
                "confmanDto",
                new Gson().toJson(
                        new ConfmanDto()
                                .setCodeApplication(appCode)
                                .setCodeParameter(paramCode)
                                .setLabel(label).setTypeParameter(typeParameter)
                )
        );

        //Confman is called
        String json = HttpCalls.post(url, map);

        if (json != null && !json.isEmpty()) {
            //We use Gson to read the instances values in the flow
            Gson gson = new Gson();
            gson.fromJson(json, ConfmanDto.class);
        }
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
    public static final class Builder extends AbstractConfmanBuilder<Builder>{
        protected String appCode;
        protected String paramCode;
        protected String paramLabel;
        protected String typeParameter = DEFAULT_TYPE_PARAM;

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
         * Set a label for the parameter.
         *
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
         *
         * @param typeParameter
         * @return
         */
        public Builder type(String typeParameter) {
            Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
            if (typeParameter != null &&
                    (DEFAULT_TYPE_PARAM.equals(typeParameter.toUpperCase()) || "INSTANCE".equals(typeParameter.toUpperCase()))) {
                this.typeParameter = typeParameter;
            }
            return this;
        }

        /**
         * Execute operation
         * @return
         * @throws IllegalStateException if the Insert has already been built, or if no column and no generated value
         * column has been specified.
         */
        public void execute() {
            Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
            built = true;
            new ConfmanAddParameter(this).execute();
        }

    }
}
