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
package com.ninjamind.confman.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * We could use a famous library for calling the Confman API, but we prefer limit the dependencies
 * of this utility library. And use Java API is more fun and sometimes easier
 * @author Guillaume EHRET
 */
public class RestCall {

    /**
     * Call a REST API
     * @param urlToCall
     * @throws RestCallException
     * @return
     */
    public String callRestApi(String urlToCall){

        //create connection
        URL url = null;
        URLConnection connection = null;
        try {
            url = new URL(urlToCall);
            connection = url.openConnection();
            connection.setAllowUserInteraction(false);
            connection.setDoOutput(true);
        } catch (IOException e) {
            throw new RestCallException("Impossible to connect to " + url.toString()  + " : Verify if the version exist!!!", e);
        }

        //get result
        StringBuffer stringBuffer = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String l = null;
            while ((l=br.readLine())!=null) {
                stringBuffer.append(l);

            }
            br.close();
        } catch (IOException e) {
            throw new RestCallException("Problem on read response " , e);
        }

        return stringBuffer.toString();
    }
}
