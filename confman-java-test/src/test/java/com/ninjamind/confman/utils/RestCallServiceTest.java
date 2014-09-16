package com.ninjamind.confman.utils;

import com.ninjamind.confman.controller.api.ParameterValueApiController;
import com.ninjamind.confman.domain.ParameterValue;
import com.ninjamind.confman.service.ParameterValueFacade;
import net.codestory.http.WebServer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Test of {@link com.ninjamind.confman.utils.RestCallService}
 *
 * @author Guillaume EHRET
 */
public class RestCallServiceTest {
    @Mock
    private ParameterValueFacade parameterValueFacade;

    /**
     * A confman server is launch before the tests
     */
    @BeforeClass
    public void setUp(){
        ParameterValueApiController restController = new ParameterValueApiController();
        restController.setParameterValueFacade(parameterValueFacade);
        new WebServer(routes -> routes.add(restController)).startOnRandomPort();
    }

    private List<ParameterValue> getParameterValues(){
//        List<ParameterValueDto>       test:
//        test.stre
//        return Streams.Lists.newArrayList(
//                new ParameterValueDto().setId(1L).setCode("test").setLabel("value"),
//                new ParameterValueDto().setId(1L).setCode("test").setLabel("value"));
        return null;
    }

    @Test
    public void test(){
        when(parameterValueFacade.findParamatersByCodeVersionAndEnv(anyString(), anyString(), anyString())).thenReturn(getParameterValues());
         String urlToCall= "";
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
    }
}
