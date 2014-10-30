package com.ninjamind.confman.utils.rest;

import com.ninjamind.confman.utils.Preconditions;
import com.ninjamind.confman.utils.rest.HttpCallException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Rest call are similar. They all used a Http connexion. This class group all the common code
 * @author Guillaume EHRET
 */
public abstract class AbstractHttpCall {

    /**
     * Call a remote service via HTTP
     * @param urlToCall
     * @return
     */
    public String execute(String urlToCall, Map<String, String> args) {
        Preconditions.checkNotNull(urlToCall, "URL is required");
        HttpClient client = HttpClientBuilder.create().build();
        BufferedReader br = null;
        StringBuffer stringBuffer = new StringBuffer();

        try {
            HttpRequestBase request = getRequest(urlToCall);
            setParameters(request, args);
            HttpResponse response = client.execute(request);

            int status = response.getStatusLine().getStatusCode();
            String message = "Erreur HTTP %d (%s) on request %s";
            String messageDetail = null;
            switch(status){
                case 400:
                    messageDetail = "Bad Request";
                    break;
                case 401:
                    messageDetail = "Unauthorized";
                    break;
                case 403:
                    messageDetail = "Forbidden";
                    break;
                case 404:
                    messageDetail = "Not found";
                    break;
                default:
                    messageDetail = "See HTTP code signification";
            }
            if(status>299){
                throw new HttpCallException(String.format(message, status, messageDetail, urlToCall));
            }
           // if(response.getStatusLine() sdfsf a faure pour verifier validite)
            br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String l = null;
            while ((l = br.readLine()) != null) {
                stringBuffer.append(l);
            }
        } catch (IOException e) {
            throw new HttpCallException("Error when execute " + urlToCall, e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new HttpCallException(e);
                }
            }
        }
        return stringBuffer.toString();
    }

    protected abstract HttpRequestBase getRequest(String urlToCall);

    protected abstract void setParameters(HttpRequestBase request, Map<String, String> args);

}
