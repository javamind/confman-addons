package com.ninjamind.confman.service;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test of {@link com.ninjamind.confman.service.RestCall}
 *
 * @author Guillaume EHRET
 */
public class RestCallTest {
    private RestCall restCall = new RestCall();

    @Test
    public void restCallShouldFindData() {
        assertThat(restCall.callRestApi("http://www.google.fr")).isNotNull();
    }

    @Test(expected = RestCallException.class)
    public void restCallShouldThrowExceptionWhenUrlInvalid() {
        restCall.callRestApi("http://localh.0.0");
    }

    @Test(expected = NullPointerException.class)
    public void restCallShouldThrowExceptionWhenUrlIsNull() throws Exception {
        restCall.callRestApi(null);
    }
}
