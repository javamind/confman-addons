package com.ninjamind.confman.call;

import com.google.common.base.Objects;
import com.ninjamind.confman.Operations;
import com.ninjamind.confman.validator.ObjectTypeValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

/**
 * @author Guillaume EHRET
 */
public class ReadData {

    /**
     * Logger
     */
    private static Logger LOG = LogManager.getLogger();

    public static void readData(String objectType, Properties properties){
        LOG.info("READ " + objectType);
        LOG.info("====");

//        switch (objectType){
//            case ObjectTypeValidator.OBJECT_INSTANCE:
//                Operations
//                        .readInstance(properties.getProperty("confman.server.name"))
//                        .onPort(Integer.valueOf(Objects.firstNonNull(properties.getProperty("confman.server.port"), "8080")))
//                        .forApp()
//                break;
//            case ObjectTypeValidator.OBJECT_PARAM:
//                break;
//            case ObjectTypeValidator.OBJECT_VALUE:
//                break;
//            case ObjectTypeValidator.OBJECT_VERSION:
//
//        }

    }
}
