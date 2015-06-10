package com.nitorcreations.puggly.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LoggedExchangeTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(LoggedExchange.class).withRedefinedSuperclass().suppress(Warning.STRICT_INHERITANCE).verify();
    }

    @Test
    public void to_string() {
        LoggedExchange exchange = new LoggedExchange(new LoggedRequest(), new LoggedResponse());
        assertThat(exchange.toString(), is("LoggedExchange[id=1,request=LoggedRequest[uri=<null>,contentType=<null>,sessionId=<null>,body=<null>],response=LoggedResponse[contentType=<null>,body=<null>]]"));
    }
}