package com.ninjamind.confman.operation;

import com.ninjamind.confman.dto.ConfmanDto;
import com.ninjamind.confman.operation.ConfmanAddParameter;
import net.codestory.http.WebServer;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test of {@link com.ninjamind.confman.operation.ConfmanAddParameter}
 *
 * @author Guillaume EHRET
 */
public class ConfmanAddParameterTest {
    private static WebServer webServer;


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
