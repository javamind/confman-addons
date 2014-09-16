package com.ninjamind.confman.utils.rest;

import com.ninjamind.confman.utils.rest.HttpCallException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

/**
 * Call HTTP wia method GET
 * @author Guillaume EHRET
 */
public class HttpPostCall extends AbstractHttpCall {
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
        try {
            if(args!=null) {
                for (String arg : args) {
                    ((HttpPost) request).setEntity(new StringEntity(arg));
                }
            }
        }
        catch (UnsupportedEncodingException e){
            throw new HttpCallException(e);
        }
    }
}
