package com.ninjamind.confman.utils;

import com.ninjamind.confman.utils.rest.HttpGetCall;

/**
 * @author Guillaume EHRET
 */
public class HttpCalls {
    /**
     * HTTP GET caller
     */
    private static HttpGetCall httpGetCall = new HttpGetCall();

    /**
     * Call urlToCall via method GET
     * @param urlToCall
     * @return
     */
    public static String get(String urlToCall){
        return httpGetCall.execute(urlToCall);
    }


    /**
     * Call urlToCall via method POST
     * @param urlToCall
     * @param args
     * @return
     */
    public static String post(String urlToCall, String ... args){
        return httpGetCall.execute(urlToCall, args);
    }
}
