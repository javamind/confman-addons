/**
 * Copyright (C) 2014 all@dev-mind.fr
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package com.ninjamind.confman.utils;

import com.google.gson.Gson;
import com.ninjamind.confman.dto.ConfmanDto;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * We could use a famous library for calling the Confman API, but we prefer limit the dependencies
 * of this utility library. And use Java API is more fun and sometimes easier
 * @author Guillaume EHRET
 */
public class RestCallService {

    /**
     * Call a remote service via HTTP method GET
     * @param urlToCall
     * @return
     */
    public String get(String urlToCall) {
        HttpClient client = HttpClientBuilder.create().build();
        BufferedReader br = null;
        StringBuffer stringBuffer = new StringBuffer();

        try {
            HttpGet request = new HttpGet(urlToCall);
            HttpResponse response = client.execute(request);
            br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String l = null;
            while ((l = br.readLine()) != null) {
                stringBuffer.append(l);
            }
        } catch (IOException e) {
            throw new RestCallException("Error when execute " + urlToCall, e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RestCallException(e);
                }
            }
        }
        return stringBuffer.toString();
    }

    /**
     * Call a remote service via HTTP method POST
     * @param urlToCall
     * @return
     */
    public String post(String urlToCall, String param) {
        HttpClient client = HttpClientBuilder.create().build();
        BufferedReader br = null;
        StringBuffer stringBuffer = new StringBuffer();

        try {
            HttpPost  request = new HttpPost(urlToCall);
            request.setEntity(new StringEntity(param));
            HttpResponse response = client.execute(request);
            br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String l = null;
            while ((l = br.readLine()) != null) {
                stringBuffer.append(l);
            }
        } catch (IOException e) {
            throw new RestCallException("Error when execute " + urlToCall, e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RestCallException(e);
                }
            }
        }
        return stringBuffer.toString();
    }


}
