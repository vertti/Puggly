package com.nitorcreations.puggly.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class LoggedExchangeTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(LoggedExchange.class).withRedefinedSuperclass().suppress(Warning.STRICT_INHERITANCE).verify();
    }

    @Test
    public void to_string() {
        final LoggedRequest request = new LoggedRequest();
        final LoggedResponse response = new LoggedResponse();

        LoggedExchange exchange = new LoggedExchange(request, response);

        assertThat(exchange.toString(), containsString(request.toString()));
        assertThat(exchange.toString(), containsString(response.toString()));
    }
}