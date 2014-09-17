package com.ninjamind.confman.utils;

import com.ninjamind.confman.utils.rest.HttpGetCall;
import com.ninjamind.confman.utils.rest.HttpPostCall;

import java.util.Map;

/**
 * @author Guillaume EHRET
 */
public class HttpCalls {
    /**
     * HTTP GET caller
     */
    private static HttpGetCall httpGetCall = new HttpGetCall();
    /**
     * HTTP POST caller
     */
    private static HttpPostCall httpPostCall = new HttpPostCall();

    /**
     * Call urlToCall via method GET
     * @param urlToCall
     * @return
     */
    public static String get(String urlToCall){
        return httpGetCall.execute(urlToCall, null);
    }


    /**
     * Call urlToCall via method POST
     * @param urlToCall
     * @param args
     * @return
     */
    public static String post(String urlToCall, Map<String, String> args){
        return httpPostCall.execute(urlToCall, args);
    }
}
