package com.ninjamind.confman.operation;

import com.ninjamind.confman.dto.ConfmanDto;
import com.ninjamind.confman.operation.ConfmanReadParameterValues;
import net.codestory.http.WebServer;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test of {@link com.ninjamind.confman.operation.ConfmanReadParameterValues}
 *
 * @author Guillaume EHRET
 */
public class ConfmanReadParameterValuesTest {
    private static WebServer webServer;

    /**
     * A confman server is launch before the tests
     */

    @BeforeClass
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        webServer = new WebServer(
                routes -> routes.get("/api/paramvalue/APP/version/1.0.0/env/dev",
                        Arrays.asList(
                                new ConfmanDto().setId(17L).setCode("jdbc.url").setLabel("jdbc:oracle:thin:@oradev:1521:ORA"),
                                new ConfmanDto().setId(21L).setCode("server.name").setLabel("WP450").setCodeInstance("WP450"))
                        )
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

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenReadPropertiesWithServerNull(){
        ConfmanReadParameterValues.from(null).onPort(8080).forApp("APP").env("dev").version("1.0.0").execute();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenReadPropertiesWithAppCodeNull(){
        ConfmanReadParameterValues.from("server").onPort(8080).env("dev").version("1.0.0").execute();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenReadPropertiesWithVersionNumberNull(){
        ConfmanReadParameterValues.from("server").onPort(8080).forApp("APP").env("dev").execute();
    }

    @Test
    public void shouldFindPropertiesWhenReadProperties(){
        Properties properties = ConfmanReadParameterValues.from("localhost").onPort(webServer.port()).forApp("APP").env("dev").version("1.0.0").execute();

        assertThat(properties).isNotEmpty();
        //An application parameter
        assertThat(properties.get("jdbc.url")).isEqualTo("jdbc:oracle:thin:@oradev:1521:ORA");
        //as the instance is not specified, the instance is like suffix because we can have several instances per environments
        assertThat(properties.get("server.name.WP450")).isEqualTo("WP450");
        assertThat(properties.get("server.name")).isNull();
    }

    @Test
    public void shouldFindPropertiesWhenReadPropertiesByEnvAndInstance(){
        Properties properties = ConfmanReadParameterValues.from("localhost").onPort(webServer.port()).forApp("APP").env("dev").version("1.0.0").instance("WP450").execute();

        assertThat(properties).isNotEmpty();
        //An application parameter
        assertThat(properties.get("jdbc.url")).isEqualTo("jdbc:oracle:thin:@oradev:1521:ORA");
        //An instance parameter
        assertThat(properties.get("server.name")).isEqualTo("WP450");

    }

}
