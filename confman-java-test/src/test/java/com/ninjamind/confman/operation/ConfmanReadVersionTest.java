package com.ninjamind.confman.operation;

import com.ninjamind.confman.dto.ConfmanDto;
import com.ninjamind.confman.operation.ConfmanReadVersion;
import net.codestory.http.WebServer;
import org.assertj.core.api.Assertions;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Test of {@link com.ninjamind.confman.operation.ConfmanReadVersion}
 *
 * @author Guillaume EHRET
 */
public class ConfmanReadVersionTest {
    private static WebServer webServer;

    /**
     * A confman server is launch before the tests
     */

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        webServer = new WebServer(
                routes -> routes.get("/confman/version/:code/app/:app", (context, code, app) -> new ConfmanDto().setId(17L).setCode("1.0.0").setLabel("my version").setVersion("1.0.0-track1"))
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
        ConfmanReadVersion.from(null).onPort(8080).forApp("APP").version("1.0.0").execute();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenAppCodeNull() {
        ConfmanReadVersion.from("server").version("1.0.0").execute();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenCodeNull() {
        ConfmanReadVersion.from("server").onPort(8080).forApp("APP").execute();
    }

    @Test
    public void shouldReadParameter() {
        Assertions.assertThat(ConfmanReadVersion.from("localhost").onPort(webServer.port()).forApp("APP").version("1.0.0").execute().getLabel()).isEqualTo("my version");
    }


}
