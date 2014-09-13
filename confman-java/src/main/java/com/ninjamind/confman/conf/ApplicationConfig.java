package com.ninjamind.confman.conf;

import com.ninjamind.confman.service.Preconditions;

/**
 * An object which returns the application properties to communicate with confman server. To call confman
 * we need
 * <ul>
 *     <li>Code Application : required</li>
 *     <li>Code Environment</li>
 *     <li>Application version number</li>
 *     <li>Code instance</li>
 *     <li>Code parameter</li>
 *     <li>Value</li>
 * </ul>
 * <pre>
 *     ApplicationConfig.application("APP").version("1.0.0").env("dev").build()
 * </pre>
 * @author Guillaume EHRET
 */
 public class ApplicationConfig implements ConfChecker<ApplicationConfig>{
    private String appCode;
    private String versionNumber;
    private String envCode;
    private String paramCode;
    private String instanceCode;
    private String value;

    private ApplicationConfig(Builder builder) {
        this.appCode = builder.appCode;
        this.versionNumber = builder.versionNumber;
        this.envCode = builder.envCode;
        this.instanceCode = builder.instanceCode;
        this.paramCode=builder.paramCode;
        this.value=builder.value;
    }

    /**
     * Construct the builder
     * @param appCode
     * @return
     */
    public static Builder application(String appCode){
        return new Builder(appCode);
    }

    public String getAppCode() {
        return appCode;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public String getEnvCode() {
        return envCode;
    }

    public String getInstanceCode() {
        return instanceCode;
    }

    public String getParamCode() {
        return paramCode;
    }

    public String getValue() {
        return value;
    }

    @Override
    public ApplicationConfig check() {
        Preconditions.checkNotNull(appCode, "code application is required");
        return this;
    }

    /**
     * A builder used to create the definition of an application in confman
     * @author Guillaume EHRET
     */
    public static final class Builder {
        public static final String ALREADY_BEEN_BUILT = "The application conf has already been built";
        private String appCode;
        private String versionNumber;
        private String envCode;
        private String instanceCode;
        private String paramCode;
        private String value;
        private boolean built;

        /**
         * Construct a new Builder.Set the code application
         * @param appCode
         */
        public Builder(String appCode) {
            this.appCode = appCode;
        }

        /**
         * Set application version number.
         * @param versionNumber
         * @return
         */
        public Builder version(String versionNumber) {
            Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
            this.versionNumber = versionNumber;
            return this;
        }

        /**
         * Set instance code.
         * @param instanceCode
         * @return
         */
        public Builder instance(String instanceCode) {
            Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
            this.instanceCode = instanceCode;
            return this;
        }

        /**
         * Set environment code.
         * @param envCode
         * @return
         */
        public Builder env(String envCode) {
            Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
            this.envCode = envCode;
            return this;
        }

        /**
         * Set parameter code.
         * @param paramCode
         * @return
         */
        public Builder param(String paramCode) {
            Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
            this.paramCode = paramCode;
            return this;
        }

        /**
         * Set label.
         * @param value
         * @return
         */
        public Builder value(String value) {
            Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
            this.value = value;
            return this;
        }

        /**
         * Builds the ApplicationConfig.
         * @return
         * @throws IllegalStateException if the Insert has already been built, or if no column and no generated value
         * column has been specified.
         */
        public ApplicationConfig build() {
            Preconditions.checkState(!built, ALREADY_BEEN_BUILT);
            built = true;
            return new ApplicationConfig(this);
        }
    }
}
