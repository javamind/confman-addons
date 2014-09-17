package com.ninjamind.confman.utils.rest;

import com.ninjamind.confman.utils.rest.HttpCallException;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Call HTTP wia method GET
 * @author Guillaume EHRET
 */
public class HttpPostCall extends AbstractHttpCall {
    @Override
    protected HttpRequestBase getRequest(String urlToCall) {
        return new HttpPost(urlToCall);
    }

    /**
     * We have no parameter in a get. All of them are in the URL
     * @param request
     * @param args
     */
    @Override
    protected void setParameters(HttpRequestBase request, Map<String, String> args) {
        try {
            if(args!=null) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> arg : args.entrySet()) {
                    nvps.add(new BasicNameValuePair(arg.getKey(), arg.getValue()));
                }
                ((HttpPost) request).setEntity(new UrlEncodedFormEntity(nvps));
            }
        }
        catch (UnsupportedEncodingException e){
            throw new HttpCallException(e);
        }
    }
}
