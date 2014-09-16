package com.ninjamind.confman.utils.rest;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * Call HTTP wia method GET
 * @author Guillaume EHRET
 */
public class HttpGetCall extends AbstractHttpCall {
    @Override
    protected HttpRequestBase getRequest(String urlToCall) {
        return new HttpGet(urlToCall);
    }

    /**
     * We have no parameter in a get. All of them are in the URL
     * @param request
     * @param args
     */
    @Override
    protected void setParameters(HttpRequestBase request, String ... args) {
        //Nothing
    }
}
