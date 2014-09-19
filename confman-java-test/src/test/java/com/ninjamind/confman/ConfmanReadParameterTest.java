package com.ninjamind.confman;

import com.ninjamind.confman.dto.ConfmanDto;
import net.codestory.http.WebServer;
import org.assertj.core.api.Assertions;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Test of {@link ConfmanReadParameter}
 *
 * @author Guillaume EHRET
 */
public class ConfmanReadParameterTest {
    private static WebServer webServer;

    /**
     * A confman server is launch before the tests
     */

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        webServer = new WebServer(
                routes -> routes.get("/confman/param/:code/app/:app", (context, code, app) -> new ConfmanDto().setId(17L).setCode("jdbc.url").setLabel("JDBC URL"))
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
        ConfmanReadParameter.from(null).onPort(8080).forApp("APP").code("jdbc.url").execute();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenAppCodeNull() {
        ConfmanReadParameter.from("server").code("jdbc.url").execute();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenCodeNull() {
        ConfmanReadParameter.from("server").onPort(8080).forApp("APP").execute();
    }

    @Test
    public void shouldReadParameter() {
        Assertions.assertThat(ConfmanReadParameter.from("localhost").onPort(webServer.port()).forApp("APP").code("jdbc.url").execute().getLabel()).isEqualTo("JDBC URL");
    }


}
