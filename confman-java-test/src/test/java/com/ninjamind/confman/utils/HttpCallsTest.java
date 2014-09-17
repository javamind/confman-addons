package com.ninjamind.confman.utils;

import com.google.gson.Gson;
import com.ninjamind.confman.controller.api.ParameterValueApiController;
import com.ninjamind.confman.domain.Parameter;
import com.ninjamind.confman.domain.ParameterValue;
import com.ninjamind.confman.dto.ConfmanDto;
import com.ninjamind.confman.service.ParameterValueFacade;
import net.codestory.http.WebServer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.assertj.core.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Test of {@link com.ninjamind.confman.utils.HttpCalls}
 *
 * @author Guillaume EHRET
 */
public class HttpCallsTest {

    private static WebServer webServer;

    /**
     * A confman server is launch before the tests
     */

    @BeforeClass
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        webServer = new WebServer(
                routes -> routes
                        .get("/confman/paramvalue/:test", (context, test) -> new ParameterValue().setId(1L).setCode("test").setLabel(test))
                        .post("/confman/paramvalue", (context) -> {
                            ConfmanDto dto = new Gson().fromJson(context.get("paramvalue"),ConfmanDto.class);
                            return new ParameterValue().setId(1L).setCode("test").setLabel(dto.getLabel());
                        })
        ).startOnRandomPort();
    }

    /**
     * At the end the server is stopped
     * @throws Exception
     */
    @AfterClass
    public void tearDown() throws Exception {
        if(webServer!=null){
            webServer.stop();
        }
    }



    @Test
    public void httpGetShouldReceiveJson(){
        //We try a get call
        String result = HttpCalls.get(String.format("http://localhost:%d/confman/paramvalue/testappelconfman", webServer.port()));
        assertThat(result).isNotEmpty();
        assertThat(new Gson().fromJson(result, ParameterValue.class).getLabel()).isEqualTo("testappelconfman");
    }


    @Test
    public void httpGetShouldNotReceiveJsonWhenUrlInvalid(){
        //We try a get call
        String result = HttpCalls.get(String.format("http://localhost:%d/cozerzerzer/testappelconfman", webServer.port()));
        //Http fluent return a HTML page with an error
        assertThat(result).isNotEmpty().contains("Page not found");
    }

    @Test
    public void httpGetShouldThrowExceptionWhenUrlNull(){
        try {
            HttpCalls.get(null);
        }
        catch (Exception e){
            assertThat(e).isInstanceOf(IllegalArgumentException.class).hasMessage("URL is required");
        }

    }

    @Test
    public void httpPostShouldReceiveJson(){
        Map<String, String> map = new HashMap<>();
        map.put("paramvalue", new Gson().toJson(new ConfmanDto().setLabel("MonLabel")));
        //We try a get call
        String result = HttpCalls.post(String.format("http://localhost:%d/confman/paramvalue", webServer.port()), map);
        assertThat(result).isNotEmpty();
        assertThat(new Gson().fromJson(result, ParameterValue.class).getLabel()).isEqualTo("MonLabel");
    }


    @Test
    public void httpPostShouldNotReceiveJsonWhenUrlInvalid(){
        //We try a get call
        String result = HttpCalls.post(String.format("http://localhost:%d/cozerzerzer/testappelconfman", webServer.port()), null);
        //Http fluent return a HTML page with an error
        assertThat(result).isNotEmpty().contains("Page not found");
    }

    @Test
    public void httpPostShouldThrowExceptionWhenUrlNull(){
        try {
            HttpCalls.post(null, null);
        }
        catch (Exception e){
            assertThat(e).isInstanceOf(IllegalArgumentException.class).hasMessage("URL is required");
        }

    }
}
