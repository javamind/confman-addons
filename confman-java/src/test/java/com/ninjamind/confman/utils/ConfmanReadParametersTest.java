package com.ninjamind.confman.utils;

import com.ninjamind.confman.ConfmanReadParameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Test of {@link com.ninjamind.confman.ConfmanReadParameters}
 *
 * @author Guillaume EHRET
 */
@RunWith(MockitoJUnitRunner.class)
public class ConfmanReadParametersTest {
    private static String callByAppAndVersionAndEnv = "[{\"id\":17,\"code\":\"jdbc.url\",\"label\":\"jdbc:oracle:thin:@oradev:1521:ORA\",\"version\":1,\"active\":true,\"oldValue\":null,\"idTrackingVersion\":1,\"codeTrackingVersion\":\"3.0.0-track.1\",\"idApplication\":1,\"codeApplication\":\"SALES\",\"idParameter\":1,\"idInstance\":null,\"codeInstance\":null,\"idEnvironment\":3,\"codeEnvironment\":\"PRD\",\"toDelete\":false},{\"id\":18,\"code\":\"jdbc.username\",\"label\":\"SALESUSR\",\"version\":1,\"active\":true,\"oldValue\":null,\"idTrackingVersion\":1,\"codeTrackingVersion\":\"3.0.0-track.1\",\"idApplication\":1,\"codeApplication\":\"SALES\",\"idParameter\":2,\"idInstance\":null,\"codeInstance\":null,\"idEnvironment\":3,\"codeEnvironment\":\"PRD\",\"toDelete\":false},{\"id\":19,\"code\":\"jdbc.password\",\"label\":\"SALESPWD\",\"version\":1,\"active\":true,\"oldValue\":null,\"idTrackingVersion\":1,\"codeTrackingVersion\":\"3.0.0-track.1\",\"idApplication\":1,\"codeApplication\":\"SALES\",\"idParameter\":3,\"idInstance\":null,\"codeInstance\":null,\"idEnvironment\":3,\"codeEnvironment\":\"PRD\",\"toDelete\":false},{\"id\":20,\"code\":\"jdbc.driver\",\"label\":\"oracle.jdbc.driver.OracleDriver\",\"version\":1,\"active\":true,\"oldValue\":null,\"idTrackingVersion\":1,\"codeTrackingVersion\":\"3.0.0-track.1\",\"idApplication\":1,\"codeApplication\":\"SALES\",\"idParameter\":4,\"idInstance\":null,\"codeInstance\":null,\"idEnvironment\":3,\"codeEnvironment\":\"PRD\",\"toDelete\":false},{\"id\":21,\"code\":\"server.name\",\"label\":\"WP450\",\"version\":1,\"active\":true,\"oldValue\":null,\"idTrackingVersion\":1,\"codeTrackingVersion\":\"3.0.0-track.1\",\"idApplication\":1,\"codeApplication\":\"SALES\",\"idParameter\":5,\"idInstance\":3,\"codeInstance\":\"WP450\",\"idEnvironment\":3,\"codeEnvironment\":\"PRD\",\"toDelete\":false},{\"id\":22,\"code\":\"server.port\",\"label\":\"8082\",\"version\":1,\"active\":true,\"oldValue\":null,\"idTrackingVersion\":1,\"codeTrackingVersion\":\"3.0.0-track.1\",\"idApplication\":1,\"codeApplication\":\"SALES\",\"idParameter\":6,\"idInstance\":3,\"codeInstance\":\"WP450\",\"idEnvironment\":3,\"codeEnvironment\":\"PRD\",\"toDelete\":false},{\"id\":23,\"code\":\"tomcatdir\",\"label\":\"d:\\\\\\\\tomcat\",\"version\":1,\"active\":true,\"oldValue\":null,\"idTrackingVersion\":1,\"codeTrackingVersion\":\"3.0.0-track.1\",\"idApplication\":1,\"codeApplication\":\"SALES\",\"idParameter\":7,\"idInstance\":3,\"codeInstance\":\"WP450\",\"idEnvironment\":3,\"codeEnvironment\":\"PRD\",\"toDelete\":false},{\"id\":24,\"code\":\"server.loglevel\",\"label\":\"DEBUG\",\"version\":1,\"active\":true,\"oldValue\":null,\"idTrackingVersion\":1,\"codeTrackingVersion\":\"3.0.0-track.1\",\"idApplication\":1,\"codeApplication\":\"SALES\",\"idParameter\":8,\"idInstance\":3,\"codeInstance\":\"WP450\",\"idEnvironment\":3,\"codeEnvironment\":\"PRD\",\"toDelete\":false}]";


    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenReadPropertiesWithServerNull(){
        ConfmanReadParameters.from(null).onPort(8080).forApp("APP").env("dev").version("1.0.0").execute();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenReadPropertiesWithAppCodeNull(){
        ConfmanReadParameters.from("server").onPort(8080).env("dev").version("1.0.0").execute();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenReadPropertiesWithVersionNumberNull(){
        ConfmanReadParameters.from("server").onPort(8080).forApp("APP").env("dev").execute();
    }

    @Test
    public void shouldNotFindPropertiesWhenReadPropertiesWithCallReturnNull(){
        when(restCall.get(anyString())).thenReturn(null);
        //The properties should be empty
        assertThat(ConfmanReadParameters.from("server").onPort(8080).forApp("APP").env("dev").version("1.0.0").build(restCall).execute()).isNotNull().isEmpty();
   }

    @Test
    public void shouldNotFindPropertiesWhenReadPropertiesWithVersionNumberNotKnown(){
        when(restCall.get(anyString())).thenReturn("[]");
        //The properties should be empty
        assertThat(ConfmanReadParameters.from("server").onPort(8080).forApp("APP").env("dev").version("1.0.0").build(restCall).execute()).isNotNull().isEmpty();
    }

    @Test
    public void shouldFindPropertiesWhenReadProperties(){
        when(restCall.get(anyString())).thenReturn(callByAppAndVersionAndEnv);
        Properties properties = ConfmanReadParameters.from("server").onPort(8080).forApp("APP").env("dev").version("1.0.0").build(restCall).execute();

        assertThat(properties).isNotEmpty();
        //An application parameter
        assertThat(properties.get("jdbc.url")).isEqualTo("jdbc:oracle:thin:@oradev:1521:ORA");
        //as the instance is not specified, the instance is like suffix because we can have several instances per environments
        assertThat(properties.get("server.name.WP450")).isEqualTo("WP450");
        assertThat(properties.get("server.name")).isNull();
    }

    @Test
    public void shouldFindPropertiesWhenReadPropertiesByEnvAndInstance(){
        when(restCall.get(anyString())).thenReturn(callByAppAndVersionAndEnv);
        Properties properties = ConfmanReadParameters.from("server").onPort(8080).forApp("APP").env("dev").version("1.0.0").instance("WP450").build(restCall).execute();

        assertThat(properties).isNotEmpty();
        //An application parameter
        assertThat(properties.get("jdbc.url")).isEqualTo("jdbc:oracle:thin:@oradev:1521:ORA");
        //An instance parameter
        assertThat(properties.get("server.name")).isEqualTo("WP450");

    }

}
