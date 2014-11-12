package com.ninjamind.confman;

import com.ninjamind.confman.operation.*;

/**
 * @author Guillaume EHRET
 */
public class Operations {

    /**
     * Read a parameter.
     * @param server
     * @return instance of the operation buider
     */
    public static ConfmanReadParameter.Builder readParameter(String server){
        return ConfmanReadParameter.from(server);
    }

    /**
     * Add a parameter.
     * @param server
     * @return instance of the operation buider
     */
    public static ConfmanAddParameter.Builder addParameter(String server){
        return ConfmanAddParameter.from(server);
    }

    /**
     * Read an instance.
     * @param server
     * @return instance of the operation buider
     */
    public static ConfmanReadInstance.Builder readInstance(String server){
        return ConfmanReadInstance.from(server);
    }

    /**
     * Read an instance.
     * @param server
     * @return instance of the operation buider
     */
    public static ConfmanReadVersion.Builder readVersion(String server){
        return ConfmanReadVersion.from(server);
    }

    /**
     * Read the parameters values.
     * @param server
     * @return instance of the operation buider
     */
    public static ConfmanReadParameterValues.Builder readValues(String server){
        return ConfmanReadParameterValues.from(server);
    }
}
