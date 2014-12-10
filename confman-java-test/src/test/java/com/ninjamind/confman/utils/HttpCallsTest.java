package com.ninjamind.confman.utils;

import com.google.gson.Gson;
import com.ninjamind.confman.dto.ParameterConfmanDto;
import com.ninjamind.confman.dto.ParameterValueConfmanDto;
import com.ninjamind.confman.utils.rest.HttpCallException;
import net.codestory.http.WebServer;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

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
                        .get("/api/paramvalue/:test", (context, test) -> new ParameterValueConfmanDto().setId(1L).setCode("test").setLabel(test))
                        .post("/api/paramvalue", (context) -> {
                            ParameterValueConfmanDto dto = new Gson().fromJson(context.get("paramvalue"),ParameterValueConfmanDto.class);
                            return new ParameterValueConfmanDto().setId(1L).setCode("test").setLabel(dto.getLabel());
                        })
                        .put("/api/paramvalue", (context) -> {
                            ParameterValueConfmanDto dto = new Gson().fromJson(context.get("paramvalue"), ParameterValueConfmanDto.class);
                            return new ParameterValueConfmanDto().setId(1L).setCode("test").setLabel(dto.getLabel());
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
        String result = HttpCalls.get(String.format("http://localhost:%d/api/paramvalue/testappelconfman", webServer.port()));
        assertThat(result).isNotEmpty();
        assertThat(new Gson().fromJson(result, ParameterValueConfmanDto.class).getLabel()).isEqualTo("testappelconfman");
    }


    @Test
    public void httpGetShouldNotReceiveJsonWhenUrlInvalid(){
        //We try a get call
        try {
            HttpCalls.get(String.format("http://localhost:%d/cozerzerzer/testappelconfman", webServer.port()));
        } catch (HttpCallException e) {
            assertThat(e).hasMessageStartingWith("Erreur HTTP 404 (Not found)");
        }
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
        map.put("paramvalue", new Gson().toJson(new ParameterValueConfmanDto().setLabel("MonLabel")));
        //We try a get call
        String result = HttpCalls.post(String.format("http://localhost:%d/api/paramvalue", webServer.port()), map);
        assertThat(result).isNotEmpty();
        assertThat(new Gson().fromJson(result, ParameterValueConfmanDto.class).getLabel()).isEqualTo("MonLabel");
    }


    @Test
    public void httpPostShouldNotReceiveJsonWhenUrlInvalid(){
        //We try a get call
        try {
            String result = HttpCalls.post(String.format("http://localhost:%d/cozerzerzer/testappelconfman", webServer.port()), null);
        } catch (HttpCallException e) {
            assertThat(e).hasMessageStartingWith("Erreur HTTP 404 (Not found)");
        }

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

    @Test
    public void httpPutShouldReceiveJson() {
        Map<String, String> map = new HashMap<>();
        map.put("paramvalue", new Gson().toJson(new ParameterConfmanDto().setLabel("MonLabel")));
        //We try a get call
        String result = HttpCalls.put(String.format("http://localhost:%d/api/paramvalue", webServer.port()), map);
        assertThat(result).isNotEmpty();
        assertThat(new Gson().fromJson(result, ParameterValueConfmanDto.class).getLabel()).isEqualTo("MonLabel");
    }


    @Test
    public void httpPutShouldNotReceiveJsonWhenUrlInvalid() {
        //We try a get call
        try {
            String result = HttpCalls.put(String.format("http://localhost:%d/cozerzerzer/testappelconfman", webServer.port()), null);
        } catch (HttpCallException e) {
            assertThat(e).hasMessageStartingWith("Erreur HTTP 404 (Not found)");
        }

    }

    @Test
    public void httpPutShouldThrowExceptionWhenUrlNull() {
        try {
            HttpCalls.put(null, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class).hasMessage("URL is required");
        }

    }
}
