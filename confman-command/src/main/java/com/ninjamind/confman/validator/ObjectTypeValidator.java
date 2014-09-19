package com.ninjamind.confman.validator;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.IParameterValidator2;
import com.beust.jcommander.ParameterException;

import java.util.Arrays;
import java.util.List;

/**
 * Authorized objects  are
 * <ul>
 *     <li>param      : Parameter defined for one application</li>
 *     <li>instance   : Instance defined for one application</li>
 *     <li>value      : Parameters values</li>
 *     <li>version    : application version</li>
 * </ul>=");
 * @author Guillaume EHRET
 */
public class ObjectTypeValidator implements IParameterValidator {
    public final static String OBJECT_PARAM  = "param";
    public final static String OBJECT_INSTANCE  = "instance";
    public final static String OBJECT_VALUE  = "value";
    public final static String OBJECT_VERSION  = "version";
    private final static List<String> OBJECTS = Arrays.asList(OBJECT_PARAM,OBJECT_INSTANCE,OBJECT_VALUE,OBJECT_VERSION);
    @Override
    public void validate(String name, String value) throws ParameterException {
        if(!OBJECTS.contains(value)){
            throw new ParameterException("Type object is incorrect you have to choose between param, instance, value and version");
        }
    }
}
