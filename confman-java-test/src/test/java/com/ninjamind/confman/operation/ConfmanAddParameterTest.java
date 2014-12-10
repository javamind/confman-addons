package com.ninjamind.confman.operation;

import com.ninjamind.confman.dto.ParameterConfmanDto;
import net.codestory.http.WebServer;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Test of {@link com.ninjamind.confman.operation.ConfmanAddParameter}
 *
 * @author Guillaume EHRET
 */
public class ConfmanAddParameterTest {
    private static WebServer webServer;

    /**
     * A confman server is launch before the tests
     */

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        webServer = new WebServer(
                routes -> routes.get("/api/param/APP/version/1.0.0/env/dev",
                        Arrays.asList(
                                new ParameterConfmanDto().setId(17L).setCode("jdbc.url").setLabel("jdbc:oracle:thin:@oradev:1521:ORA"),
                                new ParameterConfmanDto().setId(21L).setCode("server.name").setLabel("WP450").setType("INSTANCE"))
                )
        ).startOnRandomPort();
    }

    /**
     * At the end the server is stopped
     *
     * @throws Exception
     */
    @AfterClass
    public void tearDown() throws Exception {
        if (webServer != null) {
            webServer.stop();
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenServerNull() {
        ConfmanAddParameter.from(null).onPort(8080).forApp("APP").code("jdbc.url").type("APPLICATION").label("URL JDBC").execute();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenAppCodeNull() {
        ConfmanAddParameter.from("server").code("jdbc.url").type("APPLICATION").label("URL JDBC").execute();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenCodeNull() {
        ConfmanAddParameter.from("server").onPort(8080).forApp("APP").type("APPLICATION").label("URL JDBC").execute();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenLabelNull() {
        ConfmanAddParameter.from("server").onPort(8080).forApp("APP").code("jdbc.url").type("APPLICATION").execute();
    }

}
