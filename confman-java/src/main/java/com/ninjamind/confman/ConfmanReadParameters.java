package com.ninjamind.confman;

import com.google.gson.Gson;
import com.ninjamind.confman.conf.ApplicationConfig;
import com.ninjamind.confman.conf.ServerConfig;
import com.ninjamind.confman.dto.ConfmanDto;
import com.ninjamind.confman.service.Preconditions;

import java.util.Properties;

/**
 * Call cofman to read parameters values
 * @author Guillaume EHRET
 */
public class ConfmanReadParameters extends AbstractConfmanOperation<ConfmanReadParameters, Properties>{

    /**
     * This object is created via the method {@link com.ninjamind.confman.ConfmanReadParameters#from(com.ninjamind.confman.conf.ServerConfig)}.
     * The aim is a more beautiful api
     * @param serverConfig
     */
    private ConfmanReadParameters(ServerConfig serverConfig) {
        super(serverConfig);
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
    public Properties execute() {
        appConfmanConfig.check();
        Preconditions.checkNotNull(appConfmanConfig.getVersionNumber(), "version number is required");
        Preconditions.checkNotNull(appConfmanConfig.getEnvCode(), "environment code is required");

        //URL construction
        String url = String.format("http://%s:%s/confman/paramvalue/%s/version/%s/env/%s",
                serverConfig.getServer(),
                serverConfig.getPort(),
                appConfmanConfig.getAppCode(),
                appConfmanConfig.getVersionNumber(),
                appConfmanConfig.getEnvCode());

        //Confman is called
        String json = super.getRestCall().callRestApi(url);

        Properties properties = new Properties();
        if(json!=null && !json.isEmpty()){
            //We use Gson to read the parameters values in the flow
            Gson gson = new Gson();
            ConfmanDto[] parameters = gson.fromJson(json, ConfmanDto[].class);

            for(ConfmanDto param : parameters){
                //A parameter can be linked ton an instance or not. If we ask a filter we keep the application parameters
                //and the parameters of the instance passed by the method arguments
                if(appConfmanConfig.getInstanceCode()!=null && (param.getCodeInstance()==null || param.getCodeInstance().equals(appConfmanConfig.getInstanceCode()))){
                    properties.put(param.getCode(), param.getLabel());
                }
                else if(appConfmanConfig.getInstanceCode()==null){
                    //If no filter is asked the parameters are suffixed by the instance code
                    properties.put(param.getCode().concat(param.getCodeInstance()!=null ? "" + param.getCodeInstance() : ""), param.getLabel());
                }
            }
        }
        return properties;
    }

    /**
     * Returns an operation which read all the parameters values for an applicaton in Confman
     * @param serverConfig param to call Confman
     */
    public static ConfmanReadParameters from(ServerConfig serverConfig) {
        return new ConfmanReadParameters(serverConfig);
    }

    /**
     * Complete the operation with information to describe the target application. You have to give
     * <ul>
     *     <li>application code</li>
     *     <li>environment code</li>
     *     <li>version</li>
     *     <li>instance is not required</li>
     * </ul>
     * For example
     * <pre>
     *     ApplicationConfig.application("APP").env("DEV").version("1.0.0").build()
     * </pre>
     * or
     * <pre>
     *     ApplicationConfig.application("APP").env("DEV").version("1.0.0").instance("intance1").build()
     * </pre>
     * @param appConfmanConfig param to define an application
     */
    public ConfmanReadParameters forApp(ApplicationConfig appConfmanConfig) {
        this.appConfmanConfig = appConfmanConfig;
        return this;
    }
}
