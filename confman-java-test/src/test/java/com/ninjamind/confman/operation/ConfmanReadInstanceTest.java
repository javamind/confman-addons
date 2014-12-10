package com.ninjamind.confman.operation;

import com.ninjamind.confman.dto.InstanceConfmanDto;
import net.codestory.http.WebServer;
import org.assertj.core.api.Assertions;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Test of {@link com.ninjamind.confman.operation.ConfmanReadInstance}
 *
 * @author Guillaume EHRET
 */
public class ConfmanReadInstanceTest {
    private static WebServer webServer;

    /**
     * A confman server is launch before the tests
     */

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        webServer = new WebServer(
                routes -> routes.get("/api/instance/:code/app/:app/env/:env",
                        (context, code, app, env) -> new InstanceConfmanDto().setId(17L).setCode("WP450").setLabel("My instance"))
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
        ConfmanReadInstance.from(null).onPort(8080).forApp("APP").code("wP450").env("dev").execute();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenAppCodeNull() {
        ConfmanReadInstance.from("server").code("wP450").env("dev").execute();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenCodeNull() {
        ConfmanReadInstance.from("server").onPort(8080).forApp("APP").env("dev").execute();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenCodeEnvNull() {
        ConfmanReadInstance.from("server").onPort(8080).forApp("APP").code("wP450").execute();
    }

    @Test
    public void shouldReadParameter() {
        Assertions.assertThat(ConfmanReadInstance.from("localhost").onPort(webServer.port()).forApp("APP").code("wP450").env("dev").execute().getLabel()).isEqualTo("My instance");
    }


}
