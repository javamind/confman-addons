package com.ninjamind.confman.validator;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.util.Arrays;
import java.util.List;

/**
 * Authorized parameter types are APPLICATION or INSTANCE
 * @author Guillaume EHRET
 */
public class ParameterTypeValidator implements IParameterValidator {
    private final static List<String> OBJECTS = Arrays.asList("APPLICATION","INSTANCE","application","instance");
    @Override
    public void validate(String name, String value) throws ParameterException {
        if(!OBJECTS.contains(value)){
            throw new ParameterException("Parameter type is incorrect you have to choose between INSTANCE or APPLICATION");
        }
    }
}
